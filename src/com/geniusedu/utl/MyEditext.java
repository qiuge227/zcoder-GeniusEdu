package com.geniusedu.utl;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

public class MyEditext extends EditText {

	public MyEditext(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyEditext(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initialise();
	}

	public MyEditext(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private static float DEFAULT_MIN_TEXT_SIZE = 6;
	private static float DEFAULT_MAX_TEXT_SIZE = 10;

	// Attributes
	private Paint testPaint;
	private float minTextSize, maxTextSize;

	// public CustomTextView(Context context, AttributeSet attrs) {
	// super(context, attrs);
	//
	// }

	private void initialise() {
		testPaint = new Paint();
		testPaint.set(this.getPaint());

		// max size defaults to the intially specified text size unless it is
		// too small
		maxTextSize = this.getTextSize();

		if (maxTextSize <= DEFAULT_MAX_TEXT_SIZE) {
			maxTextSize = DEFAULT_MAX_TEXT_SIZE;
		}

		minTextSize = DEFAULT_MIN_TEXT_SIZE;
	};

	private void refitText(String text, int textWidth) {
		if (textWidth > 0) {
			int availableWidth = textWidth - this.getPaddingLeft()
					- this.getPaddingRight();
			float trySize = maxTextSize;
			testPaint.setTextSize(trySize);
			while ((trySize > minTextSize)
					&& (testPaint.measureText(text) > availableWidth)) {
				trySize--;
				if (trySize <= minTextSize) {
					trySize = minTextSize;
					break;
				}
				testPaint.setTextSize(trySize);
			}
			this.setTextSize(trySize);
		}
	};

	@Override
	protected void onTextChanged(CharSequence text, int start, int before,
			int after) {
		super.onTextChanged(text, start, before, after);
		refitText(text.toString(), this.getWidth());
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		if (w != oldw) {
			refitText(this.getText().toString(), w);
		}
	}
}
