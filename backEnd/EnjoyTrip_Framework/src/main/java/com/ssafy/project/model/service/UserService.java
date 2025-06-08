package com.ssafy.project.model.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.dao.NoticeDao;
import com.ssafy.project.model.dao.UserDao;
import com.ssafy.project.model.dto.Notice;
import com.ssafy.project.model.dto.NoticeAi;
import com.ssafy.project.model.dto.User;
import com.ssafy.project.model.dto.UserAi;
import com.ssafy.project.util.ChosungSearch;
import com.ssafy.project.util.ChosungSearchData;
import com.ssafy.project.util.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService{
	private final NoticeDao noticeDao;
	private final UserDao userDao;
	private final ChosungSearch chosungSearch;

	private PasswordEncoder passwordEncoder;
	
	// user 관련
	// Spring Security로 위임하기 때문에 로그인은 필요 없음
//	public User login(String id, String password) {
//		password = passwordEncoder.encode(password);
//		System.out.println(password);
//		try {
//			if (id.contains("@")) {
//				return userDao.loginByEmail(id, password);
//			} else {
//				return userDao.loginById(id, password);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	public User selectById(String id) {
		try {
			return userDao.selectById(id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Tool(description = "지정한 ID로 사용자의 상세 정보를 조회합니다. 예시) 아무개라고 하는 유저의 정보를 알려줘. 등으로 질문합니다. "
			+ "결과에는 게시글, 북마크, 코스 등 사용자의 활동 정보도 포함될 수 있습니다."
			+ "북마크의 경우, id만 추출한 다음 getAttractionsById를 이용하여 상세정보를 가져옵니다")	
	private UserAi selectByIdAi(@ToolParam(description = "지정된 Id 혹은, 유저, 사용자, 아이디 등") String id) {
		User user = selectById(id);
		if(user == null) {
			return null;
		}
		
		System.out.println("유저AI"+user.toAi());
		return user.toAi();
	}

	public User selectByEmail(String email) {
		try {
			return userDao.selectByEmail(email);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Tool(description = "지정한 이메일 주소로 사용자의 상세 정보를 조회합니다.")	
	private UserAi selectByEmailAi(@ToolParam(description = "지정된 email혹은 이메일, 사용자") String email) {
		User user = selectByEmail(email);
		if(user == null)
			return null;
		
		return user.toAi();
	}

	public int insertUser(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		try {
			return userDao.insertUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int deleteUser(String id) {
		try {
			return userDao.deleteUser(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	// notice 관련
	public List<Notice> selectNoticeAll(Integer page) {
		page = page * 15;
		try {
			return noticeDao.selectNoticeAll(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Tool(description = "모든 공지사항 게시글을 조회합니다. page번호는 15개씩 끊어서 가져오기 때문에 어느 구간을 구할지 정하는 번호입니다. 0이 최신입니다")
	private List<NoticeAi> selectNoticeAiAll(Integer page){
		List<Notice> list = selectNoticeAll(page);
		if(list == null)
			return null;
		return list.stream().map(i -> i.toAi()).toList();
	}
	
	public Notice selectNoticeById(int id) {
		try {
			return noticeDao.selectNoticeById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Tool(description = "지정한 게시글 ID에 해당하는 공지사항의 상세 내용을 조회합니다.")
	private NoticeAi selectNoticeAiById(int id) {
		Notice notice = selectNoticeById(id);
		if(notice == null)
			return null;
		return notice.toAi();
	}
	
	public List<Notice> selectNoticeByUserId(String userId, Integer page) {
		page = page * 15;
		ChosungSearchData data = chosungSearch.process(userId);

		List<Notice> result = noticeDao.selectNoticeByUserId(new String(data.getTitleArr()), page);
		
		result = result.stream().filter((notice) ->  {
			return chosungSearch.contains(notice.getAuthorId(), data);
		}).toList();
		
		return result;
	}
	
	@Tool(description = "사용자 ID로 해당 사용자가 작성한 공지사항 목록을 조회합니다. 초성 검색을 기반으로 일부 글자만 입력해도 검색됩니다. page는 게시글을 15개씩 끊어서 가져오기 때문에 어느 구간을 가져올지 정합니다. 0이 최신입니다")
	private List<NoticeAi> selectNoticeAiByUserId(@ToolParam(description = "유저 id") String userId, @ToolParam(description = "페이지 구간") Integer page){
		List<Notice> list = selectNoticeByUserId(userId, page);
		if(list == null) {
			return null;
		}
		return list.stream().map(i -> i.toAi()).toList();
	}
	
	public List<Notice> selectNoticeByTitle(String title, Integer page) {
		page = page * 15;
		ChosungSearchData data = chosungSearch.process(title);

		List<Notice> result = noticeDao.selectNoticeByTitle(new String(data.getTitleArr()), page);
		
		result = result.stream().filter((notice) ->  {
			return chosungSearch.contains(notice.getTitle(), data);
		}).toList();
		
		return result;
	}
	
	@Tool(description = "공지사항 제목으로 게시글을 검색합니다. 초성 검색이 가능하며, 일부 키워드로도 검색이 됩니다. page는 게시글을 15개씩 끊어서 가져오기 때문에 어느구간을 가져올지 정합니다. 0이 최신입니다.")
	private List<NoticeAi> selectNoticeAiByTitle(String title, Integer page){
		List<Notice> list = selectNoticeByTitle(title, page);
		
		if(list == null) {
			return null;
		}
		return list.stream().map(i -> i.toAi()).toList();
	}
	
	public int insertNotice(Notice notice) {
		try {
			return noticeDao.insertNotice(notice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	public int updateNotice(Notice notice) {
		try {
			return noticeDao.updateNotice(notice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	public int deleteNotice(int id) {
		try {
			return noticeDao.deleteNotice(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public int updateUser(User user) {
		try {
			return userDao.updateUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
