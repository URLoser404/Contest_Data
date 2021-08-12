using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;

namespace STDC_WinForm_01
{
    public partial class home : Form
    {
        SqlConnection cn = new SqlConnection("Data Source=.;Initial Catalog=STDC_WinformDB_01;Integrated Security=True;Connect Timeout=30;Encrypt=False;TrustServerCertificate=False;ApplicationIntent=ReadWrite;MultiSubnetFailover=False");
        public DataTable sql(string command)
        {
            SqlDataAdapter da = new SqlDataAdapter(command, cn);
            DataTable dt = new DataTable();
            da.Fill(dt);
            return dt;
        }
        public home()
        {
            InitializeComponent();
        }

        private void Login_Load(object sender, EventArgs e)
        {

        }

        private void btn_login_Click(object sender, EventArgs e)
        {
            try
            {
                DataTable tmp_UserCheck = sql($"select * from Account where account = '{tb_accountLogin.Text}'");
                if (tmp_UserCheck.Rows.Count > 0)
                {
                    if (tmp_UserCheck.Rows[0]["password"].ToString() == tb_passwordLogin.Text)
                    {
                        if (Convert.ToInt32(tmp_UserCheck.Rows[0]["wrong_count"]) < 3)
                        {
                            Form f = new Form();
                            switch (tmp_UserCheck.Rows[0]["permission_id"])
                            {
                                case 1:
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    break;
                                case 4:
                                    break;
                                case 8:
                                    f = new STDC_SA();
                                    break;
                            }
                            f.Show();
                            this.Hide();
                        }
                        else
                        {
                            MessageBox.Show("your account has been locked");
                        }
                    }
                    else
                    {
                        sql($"update Account set wrong_count = wrong_count+1 where account = '{tmp_UserCheck.Rows[0]["account"]}'");
                        MessageBox.Show("password wrong");
                    }
                }
                else
                {
                    MessageBox.Show("this account is not exist");
                }
                
            }
            catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        
    }
}
