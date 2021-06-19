using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

using System.Data.SqlClient;

namespace COD03
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }


        private void Form1_Load(object sender, EventArgs e)
        {
            SqlConnection cn = new SqlConnection();
            //TODO 完成連線字串，開啟資料庫，並進行資料錄設定

        }

      
        private void btnClean_Click(object sender, EventArgs e)
        {
            txtId.Text = "";
            txtName.Text = "";
            txtBasesalary.Text = "";
            txtBonus.Text = "";
            txtDeduct.Text = "";
            txtId.Enabled = true;

        }

        void Edit(string sqlstr)
        {
          
                SqlConnection cn = new SqlConnection();
            //TODO 完成連線字串，開啟資料庫，執行傳入的SQL指令
               


        }

       

        private void btnAdd_Click(object sender, EventArgs e)
        {
            //TODO 新增資料
            

            
        }

        private void btnUpdate_Click(object sender, EventArgs e)
        {
            //TODO 更新資料


            
        }

        private void btnDelete_Click(object sender, EventArgs e)
        {
            //TODO 刪除資料


           


        }

        private void btnShow_Click(object sender, EventArgs e)
        {
            //TODO 開啟Form2表單
        }
    }
}
