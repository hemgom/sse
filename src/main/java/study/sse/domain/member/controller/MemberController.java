package study.sse.domain.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import study.sse.domain.member.dto.CreateMemberRequest;
import study.sse.domain.member.dto.UpdateMemberRequest;
import study.sse.domain.member.service.MemberService;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createMember(@RequestBody CreateMemberRequest request) {
		memberService.createMember(request);
	}

	@PatchMapping
	@ResponseStatus(HttpStatus.OK)
	public void updateMember(@RequestBody UpdateMemberRequest request) {
		memberService.updateMember(request);
	}
}
