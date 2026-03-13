# STOMP 실시간 예약 구현 정리

## 1. 실시간이 필요한 부분

예약 생성 시, 아래 두 곳의 데이터를 실시간으로 동기화

- 캘린더의 `reservedDates`
- 모달의 `reservedTimes`

## 2. STOMP 구독 주소

| 구독 주소 | 설명 |
|-----------|------|
| `/topic/reservation` | 캘린더 예약 날짜 동기화 |
| `/topic/reservation/{date}` | 해당 날짜의 예약 시간 동기화 |

## 3. 트러블슈팅

### 트랜잭션 커밋 전 `convertAndSend` 호출 문제

**문제**
> 트랜잭션이 커밋되기 전에 `convertAndSend`가 실행되어 DB에 반영되지 않은 데이터가 클라이언트에 전송됨

**원인**
- `@Transactional` 메서드 내부에서 STOMP 메시지를 바로 발행

**해결**
- `@TransactionalEventListener`로 커밋 이후에 메시지 발행되도록 변경
- 해당 메서드에 `@Transactional` 적용

---

### `LocalDateTime` ↔ `String` 타입 변환 오류

**문제**

| 구분 | 내용 |
|------|------|
| 서버 | 클라이언트에서 `String`으로 넘긴 날짜를 `LocalDateTime`으로 인식 못함 |
| 클라이언트 | 서버에서 내려준 `"2024-01-01T00:00:00"` 형태가 `includes()` 비교 불일치 |

**원인**
- **서버**: JSON은 Date 타입을 지원하지 않아 String으로 전송되는데, `LocalDateTime`은 포맷 명시 없이는 자동 변환이 되지 않음
- **클라이언트**: JSON은 Date 타입을 지원하지 않아 String으로 변환 필요, 서버에서 내려온 `"2024-01-01T00:00:00"`과 `"2024-01-01"` 형태 불일치

**해결**
- **서버**
  - 경계지점(DTO)에서 `@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")`으로 명시적 변환
  - Jackson은 `@JsonFormat` 제공 + DTO ↔ JSON 형태로 자동 변환
  - `@RequestParam`으로 받은 String을 Spring `ConversionService`가 `LocalDate`로 자동 변환

- **클라이언트**
  - 서버 데이터 저장 시 `"yyyy-MM-dd"`로 변환 후 배열에 담고, 캘린더 클릭 이벤트의 Date 객체도 동일 포맷으로 변환해 `includes()` 비교
