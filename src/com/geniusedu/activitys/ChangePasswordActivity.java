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
 * �����ƣ�ChangePasswordActivity ���������޸����� �����ˣ�¬���� �޸��ˣ�¬���� �޸�ʱ�䣺2014-9-7 ����9:22:06
 * �޸ı�ע��
 * 
 * @version 1.0.0
 * 
 */
public class ChangePasswordActivity extends Activity implements OnClickListener {
	private EditText original, newword, reinputnew;// ԭʼ�����������������
	private ImageView back;
	private Button sure_bt;// ȷ���޸İ�ť
	private String stuid;// ѧ��ѧ��
	private String stupassword;// ѧ������
	private String schoolid;// ѧУ���

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
	 * findView(�󶨿ؼ�) void
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
						Toast.makeText(ChangePasswordActivity.this, "�����޸ĳɹ�",
								1000).show();
						finish();
					} else if (result.equals("2")) {
						Toast.makeText(ChangePasswordActivity.this, "�޸�ʧ��",
								1000).show();
					} else {
						Toast.makeText(ChangePasswordActivity.this,
								"���������������쳣", 1000).show();
					}
				} else {
					Toast.makeText(ChangePasswordActivity.this, "�����쳣", 1000)
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
						Toast.makeText(ChangePasswordActivity.this, "ԭʼ�������",
								1000).show();
					}

				} else {
					Toast.makeText(ChangePasswordActivity.this, "������������벻ͬ",
							1000).show();
				}
			} else {
				Toast.makeText(ChangePasswordActivity.this, "���벻��Ϊ��", 1000)
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
