package jp.co.daifuku.wms.test.query.dasch;

import jp.co.daifuku.wms.exercise.util.pager.ISQLQueryPagerImpl;
import jp.co.daifuku.wms.exercise.util.pager.SQLQueryPager;
import jp.co.daifuku.wms.exercise.util.pager.UIPager;
import jp.co.daifuku.wms.exercise.util.query.SQLQuery;
import jp.co.daifuku.wms.test.query.entity.StudentDetail;
import jp.co.daifuku.wms.test.query.entity.StudentVo;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentPager extends UIPager<StudentVo,StudentDetail>{
    @Override
    protected void setPager() {
        pager =new SQLQueryPager<StudentVo,StudentDetail>(new ISQLQueryPagerImpl<StudentVo, StudentDetail>() {
            @Override
            public SQLQuery assemblyQuery(Connection conn, StudentVo params) throws Exception {
                SQLQuery sql=new SQLQuery();
                StringBuffer sb=new StringBuffer("select s.name,s.number,s.sex,s.telephone,s.fromDate,s.fromTime,s.toDate,s.toTime,s.major," +
                        "s.address,s.hobby,s.score,s.grade from Student s  where 1=1 ");
                if(StringUtils.isNotBlank(params.getName())){
                    sb.append("and s.name=:name");
                    sql.setString("name",params.getName());
                }
                if(StringUtils.isNotBlank(String.valueOf(params.getNumber()))){
                    sb.append("and s.number=:number");
                    sql.setString("number", String.valueOf(params.getNumber()));
                }
                if(StringUtils.isNotBlank(params.getSex())){
                    sb.append("and s.sex=:sex");
                    sql.setString("sex",params.getSex());
                }
                if(StringUtils.isNotBlank(String.valueOf(params.getTelephone()))){
                    sb.append("and s.telephone=:telephone");
                    sql.setString("telephone",String.valueOf(params.getTelephone()));
                }
                if(StringUtils.isNotBlank(String.valueOf(params.getFromDate()))){
                    sb.append("and s.fromDate=:fromDate");
                    sql.setString("fromDate", String.valueOf(params.getFromDate()));
                }
                if(StringUtils.isNotBlank(String.valueOf(params.getFromTime()))){
                    sb.append("and s.fromTime=:fromTime");
                    sql.setString("fromTime", String.valueOf(params.getFromTime()));
                }
                if(StringUtils.isNotBlank(String.valueOf(params.getToDate()))) {
                    sb.append("and s.toDate=:toDate");
                    sql.setString("toDate", String.valueOf(params.getToDate()));
                }
                if(StringUtils.isNotBlank(String.valueOf(params.getToTime()))){
                    sb.append("and s.toTime=:toTime");
                    sql.setString("toTime", String.valueOf(params.getToTime()));
                }
                if(StringUtils.isNotBlank(params.getMajor())){
                    sb.append("and s.major=:major");
                    sql.setString("major",params.getMajor());
                }
                if(StringUtils.isNotBlank(params.getAddress())){
                    sb.append("and s.address=:address");
                    sql.setString("address",params.getAddress());
                }
                if(StringUtils.isNotBlank(params.getHobby())){
                    sb.append("and s.hobby=:hobby");
                    sql.setString("hobby",params.getHobby());
                }
                if(StringUtils.isNotBlank(String.valueOf(params.getScore()))){
                    sb.append("and s.score=:score");
                    sql.setString("score", String.valueOf(params.getScore()));
                }
                if(StringUtils.isNotBlank(params.getGrade())){
                    sb.append("and s.grade=:grade");
                    sql.setString("grade",params.getGrade());
                }
                sb.append(" order s.number,s.score ");
                sql.setSql(sb.toString());
                return sql;
            }


            @Override
            public List<StudentDetail> convertQueryResultList(Connection conn, List<List<Object>> list, long firstResultPos) throws Exception {
                if(list == null ){
                    return  null ;
                }
                List<StudentDetail> returnList=new ArrayList<StudentDetail>();
                for(List<Object> cols: list){
                   StudentDetail detail=new StudentDetail();
                    firstResultPos++;
                    detail.setNumber(Long.parseLong((String)cols.get(0)));
                    detail.setName(String.valueOf(cols.get(1)));
                    detail.setMajor(String.valueOf(cols.get(2)));
                    detail.setScore((Integer) cols.get(3));
                    detail.setGrade(String.valueOf(cols.get(4)));
                    detail.setToDate((Date) cols.get(5));
                    detail.setToTime((Date) cols.get(6));
//                    detail.setSex(String.valueOf(cols.get(7)));
//                    detail.setTelephone(Long.parseLong((String)cols.get(8)));
//                    detail.setFromDate((Date)cols.get(9));
//                    detail.setFromTime(String.valueOf(cols.get(10)));
//                    detail.setHobby(String.valueOf(cols.get(11)));
                    returnList.add(detail);
                }
                    return returnList;
            }
        });

    }
}
