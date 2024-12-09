package study.sse.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.sse.domain.member.dto.CreateMemberRequest;
import study.sse.domain.member.dto.UpdateMemberRequest;

@Entity
@Table(name = "MEMBERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "SELF_INTRODUCTION")
	private String selfIntroduction;

	@Builder
	public Member(String name, String selfIntroduction) {
		this.name = name;
		this.selfIntroduction = selfIntroduction;
	}

	public static Member create(CreateMemberRequest request) {
		return Member.builder()
			.name(request.getName())
			.selfIntroduction(request.getSelfIntroduction())
			.build();
	}

	public void updateInfo(UpdateMemberRequest request) {
		this.name = request.getName();
		this.selfIntroduction = request.getSelfIntroduction();
	}
}
