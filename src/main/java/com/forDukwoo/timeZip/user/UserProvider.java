package com.forDukwoo.timeZip.user;

import com.forDukwoo.timeZip.config.BaseException;
import com.forDukwoo.timeZip.user.model.GetScrapRes;
import com.forDukwoo.timeZip.user.model.GetUserInfoRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.forDukwoo.timeZip.config.BaseResponseStatus.DATABASE_ERROR;
import static com.forDukwoo.timeZip.config.BaseResponseStatus.POSTS_EMPTY_CATEGORY_ID;

@Service
public class UserProvider {
    private final UserDao userDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao) {
        this.userDao = userDao;
    }

    public int checkIdExist(String email) throws BaseException {
        try {
            return userDao.checkUserIdExist(email);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkNickExist(String nick) throws BaseException{
        try {
            return userDao.checkUserNickExist(nick);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetUserInfoRes retrieveUser(long userId) throws BaseException {
        try{
            GetUserInfoRes getUserInfoRes = userDao.selectUser(userId);
            return getUserInfoRes;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetScrapRes> retrieveScrap(int userId, String category) throws BaseException {
        try {
            if(category.equals("news")) {
                List<GetScrapRes> getScrapRes = userDao.getScrapNews(userId);
                return getScrapRes;
            }
            else if(category.equals("en_news")) {
                List<GetScrapRes> getScrapRes = userDao.getScrapEnNews(userId);
                return getScrapRes;
            }
            else if (category.equals("audio")) {
                List<GetScrapRes> getScrapRes = userDao.getScrapAudio(userId);
                return getScrapRes;
            }
            throw new BaseException(POSTS_EMPTY_CATEGORY_ID);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
