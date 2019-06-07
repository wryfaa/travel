package cn.itcast.travel.service.impl;/**
 * @author wtf
 * @date 2019/6/6 21:27
 */

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

/**
 * @program: travel
 * @description: FavoriteService的实现类
 * @author: wtf
 * @create: 2019-06-06 21:27
 **/
public class FavoriteServiceImpl implements FavoriteService {
    FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public boolean ifFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);

        return favorite != null;   //如果favorite有值，则为true，否则为false
    }

    /** 
    * @Description: 添加收藏 
    * @Param: [rid, uid] 
    * @return: void 
    */
    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid), uid);
    }
}
