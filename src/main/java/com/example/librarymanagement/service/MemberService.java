package com.example.librarymanagement.service;

import com.example.librarymanagement.model.Member;
import com.example.librarymanagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public void save(Member member) {
        memberRepository.save(member);
    }

    public Member findById(String id) {
        return memberRepository.findById(id).orElse(null);
    }

    public void deleteById(String id) {
        memberRepository.deleteById(id);
    }
    public boolean existsById(String id) {
        return memberRepository.existsById(id);
    }
}