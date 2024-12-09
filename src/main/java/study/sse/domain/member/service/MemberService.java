package study.sse.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import study.sse.domain.member.Member;
import study.sse.domain.member.dto.CreateMemberRequest;
import study.sse.domain.member.dto.UpdateMemberRequest;
import study.sse.domain.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public void createMember(CreateMemberRequest request) {
		Member createdMember = Member.create(request);
		memberRepository.save(createdMember);
	}

	@Transactional
	public void updateMember(UpdateMemberRequest request) {
		Member foundMember = memberRepository.findById(request.getMemberId())
			.orElseThrow(() -> new IllegalArgumentException("Member not found"));

		foundMember.updateInfo(request);
		memberRepository.save(foundMember);
	}
}
