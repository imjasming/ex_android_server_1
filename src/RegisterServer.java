import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterServer extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("register-doPost");
        System.out.println("register-doget");
        request.setCharacterEncoding("UTF-8");
        String user_name = request.getParameter("username");
        String user_pw = request.getParameter("password");
        PrintWriter out = response.getWriter();
        //调用数据库
        // 直接调用DB中的静态方法来为本类服务
        Connection conn = DatabaseManager.getConnection();
        Statement stmt = DatabaseManager.getStatement(conn);
        String sql =  "select * from users where user_name='" + user_name
                + "'";
        ResultSet rs = DatabaseManager.getResultSet(stmt, sql);
        try {
            if (rs.next()) {
                System.out.println("账号已存在");
                out.print("false");
            } else {
                String sqlInsert = "insert into users values('"+ user_name +"','" + user_pw + "')";
                DatabaseManager.elsql(stmt,sqlInsert);
                System.out.println("账号已增加");
                out.print("true");
            }
        } catch (SQLException e) {
            out.print("false");
            e.printStackTrace();
        }
    }

}
