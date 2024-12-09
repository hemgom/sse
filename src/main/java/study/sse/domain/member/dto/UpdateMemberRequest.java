package study.sse.domain.member.dto;

import lombok.Getter;

@Getter
public class UpdateMemberRequest {

	private Long memberId;
	private String name;
	private String selfIntroduction;
}
