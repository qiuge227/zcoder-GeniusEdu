package com.geniusedu.activitys;

import com.geniusedu.basebeans.BaseEntry;
import com.geniusedu.logical.implement.ImRequestData;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * 
 * 
 * �����ƣ�AddPointsActivity
 * ��������
 * �����ˣ�¬���� 
 * �޸��ˣ�¬���� 
 * �޸�ʱ�䣺2014-9-17 ����12:54:26
 * �޸ı�ע��
 * @version 1.0.0
 *
 */
@SuppressLint("ShowToast")
public class AddPointsActivity extends Activity implements OnClickListener {
	private EditText number_ed;// ����
	private EditText score_ed;// ����
	private Button pointsave_bt;// ȷ����ӻ���
	private String stuid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addpoint_layout);
		stuid = getIntent().getStringExtra("stuid");
		findView();
		setListener();
	}

	public void findView() {
		number_ed = (EditText) findViewById(R.id.number_ed);
		score_ed = (EditText) findViewById(R.id.score_ed);
		pointsave_bt = (Button) findViewById(R.id.pointsave_bt);
	}

	public void setListener() {
		pointsave_bt.setOnClickListener(this);
	}

	public void addScore(String number, String score) {
		new ImRequestData(AddPointsActivity.this) {

			@Override
			public void getResult(String result) {
				// TODO Auto-generated method stub
				if (result != null) {
					if (result.equals("1")) {
						Toast.makeText(AddPointsActivity.this, "��ӳɹ�", 1000)
								.show();

					} else if (result.equals("2")) {
						Toast.makeText(AddPointsActivity.this, "���ʧ��,������ɨɨ", 1000)
								.show();
					} else if (result.equals("3")) {
						Toast.makeText(AddPointsActivity.this, "��Ч��Ƭ����ʹ�ù�", 1000)
						.show();
					} else {
						Toast.makeText(AddPointsActivity.this, "���������������쳣",
								1000).show();
					}
				} else {
					Toast.makeText(AddPointsActivity.this, "�����쳣", 1000).show();
				}
			}

			@Override
			public void getBaseentry(BaseEntry baseentry) {
				// TODO Auto-generated method stub

			}
		}.getDataByNet(R.string.interface_upload_integral, stuid, score, number);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.pointsave_bt:
			if (!number_ed.getText().toString().trim().equals("")
					&& !score_ed.getText().toString().trim().equals("")) {
				addScore(number_ed.getText().toString(), score_ed.getText()
						.toString());
			} else {
				Toast.makeText(AddPointsActivity.this, "��źͻ��ֲ���Ϊ��", 1000)
						.show();
			}

			break;
		default:
			break;
		}
	}
}
