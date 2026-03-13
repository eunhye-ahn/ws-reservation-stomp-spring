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
    const fetchReservedDates = async () => {
      try {
        const res = await fetch('http://localhost:8080/api/reservations/reservedDates');
        const data = await res.json();
        setReservedDates(data.reservedDates);
      } catch (err) {
        console.log(err);
      }

    }

    const client = new Client({
      brokerURL: "ws://localhost:8080/ws",
      reconnectDelay: 5000,
      onConnect: () => {
        client.subscribe('/topic/reservation', (message) => {
          try {
            const data = JSON.parse(message.body);
            setReservedDates(data.reservedDates);
          } catch (err) {
            console.error(err);
          }
        })
      },
      onStompError: () => {
        console.log("[STOMP Error] fallback to HTTP");
        fetchReservedDates();
      },
      onDisconnect: () => {
        console.log("[STOMP Disconnect] fallback to HTTP");
        if (!client.active) fetchReservedDates();
      }
    });
    client.activate();
    clientRef.current = client;

    fetchReservedDates();

    return () => client.deactivate();
  }, []);

  //예약마감된 날짜 표시
  const dayPropGetter = (date) => {//Date 타입
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
    const dateStr = dayjs(start).format("YYYY-MM-DD");
    setSelectedDate(dateStr);
    if (reservedDates.includes(dateStr)) return;
    setIsOpen(true);
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


