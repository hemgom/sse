package study.sse.domain.notification.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import study.sse.domain.notification.LocalMemoryNotification;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

	private final LocalMemoryNotification localMemoryNotification;
	private static final long TIMEOUT = 60 * 1000;

	public SseEmitter subscribe(Long memberId) {
		SseEmitter emitter = new SseEmitter(TIMEOUT);
		localMemoryNotification.add(memberId, emitter);

		try {
			emitter.send(SseEmitter.event()
				.name("subscribe emitter")
				.data("subscribed!"));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return emitter;
	}
}
