package likelion12th.session.controller;

import likelion12th.session.domain.Member;
import likelion12th.session.dto.request.JoinRequest;
import likelion12th.session.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {
    private final MemberService memberService;

    @PostMapping("/join")
    public void join(@RequestBody JoinRequest request) {
        memberService.join(request);



    }
}
