package book.example.board.config.auth.dto;

import book.example.board.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/*
* 인증된 사용자만 담을 Dto
* 세션에 저장하기 위함
* Entity에 직렬화 기능을 추가하기엔 여려모로 좋지 않으므로
* 직렬화 기능을 추가한 Dto 클래스를 하나 더 생성하는 것이 안전 + 경제적
*/
@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
