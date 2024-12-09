package study.sse.domain.notification;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LocalMemoryNotification {

	private final Map<Long, SseEmitter> emitterMap = new ConcurrentHashMap<>();

	public SseEmitter add(Long memberId, SseEmitter emitter) {
		emitterMap.put(memberId, emitter);
		log.info("새로운 emitter 추가: {}", emitter);
		log.info("현재 emitters 크기: {}", emitterMap.size());

		emitter.onCompletion(() -> {
			emitterMap.remove(memberId);
			log.info("SSE connection terminated : id={}", memberId);
		});

		emitter.onTimeout(() -> {
			emitter.complete();
			log.info("SSE timeout : id={}", memberId);
		});

		return emitter;
	}

	public void updateMemberInfo(Long memberId, String name, String selfIntroduction) {
		SseEmitter emitter = emitterMap.get(memberId);
		try {
			emitter.send(SseEmitter.event()
				.name("update Active Info")
				.data("회원 정보가 name=" + name + ", selfIntroduction=" + selfIntroduction + " 으로 변경되었습니다.")
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
