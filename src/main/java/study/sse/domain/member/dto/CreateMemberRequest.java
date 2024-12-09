package study.sse.domain.member.dto;

import lombok.Getter;

@Getter
public class CreateMemberRequest {

	private String name;
	private String selfIntroduction;
}
