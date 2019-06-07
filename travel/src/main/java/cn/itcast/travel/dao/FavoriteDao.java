package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

/**
 * @author wtf
 * @date 2019/6/6 21:28
 */
public interface FavoriteDao {
    /**
     * @Description: 根据rid和UID查询收藏信息
     * @Param: [rid, uid]
     * @return: cn.itcast.travel.domain.Favorite
     */
    public Favorite findByRidAndUid(int rid, int uid);

    /**
     * @Description: 根据线路id查询收藏次数
     * @Param: [rid]
     * @return: int
     */
    public int findCoutByRid(int rid);

    /** 
    * @Description: 添加收藏 
    * @Param: [i, uid] 
    * @return: void 
    */
    public void add(int rid, int uid);
}
