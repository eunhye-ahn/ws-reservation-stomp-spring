// import { useEffect, useRef } from "react";
// import { Client } from "@stomp/stompjs";


// export const useStomp = () => {
//     const stompRef = useRef(null);

//     useEffect(() => {
//         //stomp client 생성
//         const client = new Client({
//             brokerURL: 'ws://localhost:8080/ws',
//             onConnect: () => {
//                 console.log('STOMP 연결')
//             },
//             onDisconnect: () => {
//                 console.log('연결끊김')
//             }
//         });
//         stompRef.current = client;
//         client.activate();
//         return () => {
//             stompRef.current.deactivate();
//         }
//     }, []);

//     const subscribe = (destination, callback) => {
//         if (stompRef.current?.connected) {
//             stompRef.current.subscribe(destination, callback);
//         } else {
//             stompRef.current.onConnect = () => {
//                 stompRef.current.subscribe(destination, callback);
//             }
//         }
//     };
//     const publish = (destination, body) => {
//         if (stompRef.current?.connected) {
//             stompRef.current.publish({ destination, body: JSON.stringify(body) });
//         } else {
//             stompRef.current.onConnect = () => {
//                 stompRef.current.publish({ destination, body: JSON.stringify(body) });
//             }
//         }
//     };

//     return { stompRef, subscribe, publish };
// }