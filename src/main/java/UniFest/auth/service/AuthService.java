package UniFest.auth.service;

import UniFest.domain.member.entity.Member;
import UniFest.domain.member.entity.MemberRole;
import UniFest.domain.member.repository.MemberRepository;
import UniFest.exception.jwt.RefreshTokenExpiredException;
import UniFest.exception.member.MemberNotFoundException;
import UniFest.security.jwt.JwtTokenizer;
import UniFest.security.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final RedisRepository redisRepository;
    private final JwtTokenizer jwtTokenizer;
    private final RedisTemplate<String, String> redisTemplate;

    public void logout(String refreshToken) {
        redisRepository.expireRefreshToken(refreshToken);
    }

    // AccessToken 재발급
    public String reissue(String refreshToken) {
        // 1차 - 리프레시 토큰 유효기한 검사
        Boolean isValidDate = jwtTokenizer.isValidDateToken(refreshToken);
        if (!isValidDate) throw new RefreshTokenExpiredException();

        // 2차 - 레디스 리프레시 토큰 존재여부 검사
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Boolean hasKey = redisTemplate.hasKey(refreshToken);

        // 레디스 토큰 유효성 검사 통과 시 엑세스 토큰 재 발급
        if (hasKey) {
            String email = valueOperations.get(refreshToken);
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new MemberNotFoundException());
            String role = member.getMemberRole().getValue();
            return jwtTokenizer.createAccessToken(member.getId(),member.getEmail(),"ROLE_" + role);
        } else {
            throw new RefreshTokenExpiredException();
        }
    }
}
