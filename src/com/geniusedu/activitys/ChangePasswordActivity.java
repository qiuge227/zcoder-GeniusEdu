package com.geniusedu.activitys;

import com.geniusedu.app.CatchApplication;
import com.geniusedu.basebeans.BaseEntry;
import com.geniusedu.logical.implement.ImRequestData;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 
 * 
 * 类名称：ChangePasswordActivity 类描述：修改密码 创建人：卢东斌 修改人：卢东斌 修改时间：2014-9-7 下午9:22:06
 * 修改备注：
 * 
 * @version 1.0.0
 * 
 */
public class ChangePasswordActivity extends Activity implements OnClickListener {
	private EditText original, newword, reinputnew;// 原始密码和新密码的输入框
	private ImageView back;
	private Button sure_bt;// 确定修改按钮
	private String stuid;// 学生学籍
	private String stupassword;// 学生密码
	private String schoolid;// 学校编号

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changepassword_layout);
		stuid = CatchApplication.stuid;
		stupassword = CatchApplication.stu_password;
		schoolid = CatchApplication.schoolid;
		findView();
		setListener();
	}

	/**
	 * 
	 * findView(绑定控件) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void findView() {
		original = (EditText) findViewById(R.id.original);
		newword = (EditText) findViewById(R.id.newword);
		reinputnew = (EditText) findViewById(R.id.reinputnew);
		sure_bt = (Button) findViewById(R.id.sure_bt);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	public void setListener() {
		sure_bt.setOnClickListener(this);

	}

	public void setNewPassword(String stuid, String original, String newword) {
		new ImRequestData(ChangePasswordActivity.this) {

			@Override
			public void getResult(String result) {
				// TODO Auto-generated method stub
				if (result != null) {
					if (result.equals("1")) {
						Toast.makeText(ChangePasswordActivity.this, "密码修改成功",
								1000).show();
						finish();
					} else if (result.equals("2")) {
						Toast.makeText(ChangePasswordActivity.this, "修改失败",
								1000).show();
					} else {
						Toast.makeText(ChangePasswordActivity.this,
								"服务器数据连接异常", 1000).show();
					}
				} else {
					Toast.makeText(ChangePasswordActivity.this, "解析异常", 1000)
							.show();
				}
			}

			@Override
			public void getBaseentry(BaseEntry baseentry) {
				// TODO Auto-generated method stub

			}
		}.getDataByNet(R.string.interface_upload_password, stuid, newword,
				schoolid);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.sure_bt:
			if (!original.getText().toString().trim().equals("")
					&& !newword.getText().toString().trim().equals("")
					&& !reinputnew.getText().toString().trim().equals("")) {
				if (newword.getText().toString().trim()
						.equals(reinputnew.getText().toString().trim())) {
					if (original.getText().toString().equals(stupassword)) {
						setNewPassword(stuid, original.getText().toString(),
								newword.getText().toString());
					} else {
						Toast.makeText(ChangePasswordActivity.this, "原始密码错误",
								1000).show();
					}

				} else {
					Toast.makeText(ChangePasswordActivity.this, "两次输入的密码不同",
							1000).show();
				}
			} else {
				Toast.makeText(ChangePasswordActivity.this, "密码不能为空", 1000)
						.show();
			}

			break;

		default:
			break;
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.overridePendingTransition(R.anim.flip_horizontal_in,
				R.anim.flip_horizontal_out);
		super.onPause();
	}
}
