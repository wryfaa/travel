package cn.itcast.travel.service;

/**
 * @author wtf
 * @date 2019/6/6 21:26
 */
public interface FavoriteService {
    public boolean ifFavorite(String rid, int uid);

    public void add(String rid, int uid);
}
