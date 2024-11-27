

package pri.yqx.good.domain.json;

import com.alibaba.fastjson.JSON;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class PicUrlJsonHandler extends BaseTypeHandler<List<PicUrl>> {
    public PicUrlJsonHandler() {
    }

    public void setNonNullParameter(PreparedStatement ps, int i, List<PicUrl> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSON.toJSONString(parameter));
    }

    public List<PicUrl> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return JSON.parseArray(rs.getString(columnName), PicUrl.class);
    }

    public List<PicUrl> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return JSON.parseArray(rs.getString(columnIndex), PicUrl.class);
    }

    public List<PicUrl> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return JSON.parseArray(cs.getString(columnIndex), PicUrl.class);
    }
}
