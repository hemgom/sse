package study.sse.domain.notification;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;
import study.sse.domain.notification.enums.SubscribeType;

@Slf4j
@Component
public class LocalMemoryNotification {

	// 하나의 SseEmitter 를 사용하도록 테스트해본 결과 마지막에 subscribe 한 요청만 이벤트 응답을 받는 것을 확인
	// 이벤트 스트림의 'id' 필드는 마지막 이벤트 ID 값을 설정하기 위한 용도
	// 'id' 를 지정한다고 해당 id 로 이벤트 응답을 받은 요청만이 이벤트 응답을 받지는 않는다.
	// 결국 하나의 요청에 하나의 SseEmitter 가 필요하다는 것을 확인함
	private final Map<SubscribeType, SseEmitter> emitterMap = new ConcurrentHashMap<>();

	public SseEmitter add(SseEmitter emitter, SubscribeType subscribeType) {
		emitterMap.put(subscribeType, emitter);
		log.info("새로운 emitter 추가: {}", emitter);
		log.info("현재 emitters 크기: {}", emitterMap.size());

		emitter.onCompletion(() -> {
			emitterMap.remove(subscribeType);
			log.info("SSE connection terminated : key={}", subscribeType);
		});

		emitter.onTimeout(() -> {
			emitter.complete();
			log.info("SSE timeout : key={}", subscribeType);
		});

		return emitter;
	}

	public void updateMemberInfo(Long memberId, String name, String selfIntroduction) {
		SseEmitter emitter = emitterMap.get(SubscribeType.ACTIVITY);
		try {
			emitter.send(SseEmitter.event()
				.id(String.valueOf(memberId))
				.name("update Active Info")
				.data("회원 정보가 name=" + name + ", selfIntroduction=" + selfIntroduction + " 으로 변경되었습니다.")
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
