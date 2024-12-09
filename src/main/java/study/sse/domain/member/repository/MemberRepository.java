package study.sse.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import study.sse.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
