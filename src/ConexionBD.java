public class ConexionBD {

    public Connection getconnection()
    {
        getconnection() con = null;

        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ferreteria","root","");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return con;
    }

}

