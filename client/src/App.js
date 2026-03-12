import { Calendar, momentLocalizer } from 'react-big-calendar'
import moment from 'moment'
import 'react-big-calendar/lib/css/react-big-calendar.css'
import { useEffect, useRef, useState } from 'react';
import dayjs from 'dayjs';
import ReservationModal from './component/ReservationModal';
import { Client } from '@stomp/stompjs';

const localizer = momentLocalizer(moment);

function App() {

  const [reservedDates, setReservedDates] = useState([]);
  const [isOpen, setIsOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [selectedDate, setSelectedDate] = useState(null);

  const clientRef = useRef(null);

  useEffect(() => {
    //stomp 연결 초기화 (앱 진입시 1회)
    const client = new Client({
      brokerURL: "ws://localhost:8080/ws",
      onConnect: () => {
        console.log("연결시작")
        client.subscribe('/topic/reservation', (message) => {
          const data = JSON.parse(message.body);
          console.log(data);
          setReservedDates(data.reservedDates);
        })
      }
    });
    client.activate();
    clientRef.current = client;

    const fetchReservedDates = async () => {
      try {
        const res = await fetch('http://localhost:8080/api/reservations/reservedDates');
        const data = await res.json();
        setReservedDates(data.reservedDates);
      } catch (err) {
        console.log(err);
      }

    }
    fetchReservedDates();
  }, []);

  //예약마감된 날짜 표시
  const dayPropGetter = (date) => {
    const isReserved = reservedDates.includes(dayjs(date).format("YYYY-MM-DD"));
    if (isReserved) {
      return {
        style: {
          backgroundColor: "gray",
          cursor: "not-allowed",
        }
      }
    }
  }

  const handleDateClick = ({ start }) => {
    setSelectedDate(start)
    if (reservedDates.includes(dayjs(start).format("YYYY-MM-DD"))) return;
    setIsOpen(true)
  }

  return (
    <div >
      <Calendar
        localizer={localizer}
        events={[]}
        startAccessor="start"
        endAccessor="end"
        style={{ height: 500 }}

        dayPropGetter={dayPropGetter}

        selectable
        onSelectSlot={handleDateClick}
      />
      {isOpen && <ReservationModal date={selectedDate}
        client={clientRef.current}
        onClose={() => { setIsOpen(false) }} />}
    </div>
  );
}
export default App;


