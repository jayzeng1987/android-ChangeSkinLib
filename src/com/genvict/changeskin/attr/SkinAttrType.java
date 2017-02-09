package com.genvict.changeskin.attr;

import java.lang.reflect.Field;

import com.genvict.changeskin.ResourceManager;
import com.genvict.changeskin.SkinManager;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public enum SkinAttrType {
	BACKGROUND("background") {
		@Override
		public void apply(View view, String resName) {
			Drawable drawable = getResourceManager().getDrawableByName(resName);
			if (drawable != null) {
				//view.setBackgroundDrawable(drawable);
				view.setBackground(drawable);
			} else {
				try {
					int color = getResourceManager().getColor(resName);
					view.setBackgroundColor(color);
				} catch (Resources.NotFoundException ex) {
					ex.printStackTrace();
				}
			}
		}
	},
	COLOR("textColor") {
		@Override
		public void apply(View view, String resName) {
			ColorStateList colorList = getResourceManager().getColorStateList(resName);
			if (colorList == null)
				return;
			((TextView) view).setTextColor(colorList);
		}
	},
	SRC("src") {
		@Override
		public void apply(View view, String resName) {
			if (view instanceof ImageView) {
				Drawable drawable = getResourceManager().getDrawableByName(resName);
				if (drawable == null)
					return;
				((ImageView) view).setImageDrawable(drawable);
			}

		}
	},
	DIVIDER("divider") {
		@Override
		public void apply(View view, String resName) {
			if (view instanceof ListView) {
				Drawable divider = getResourceManager().getDrawableByName(resName);
				if (divider == null)
					return;
				((ListView) view).setDivider(divider);
			} else if (view instanceof LinearLayout) {
				Drawable divider = getResourceManager().getDrawableByName(resName);
				if (divider == null)
					return;
				((LinearLayout) view).setDividerDrawable(divider);
			}
		}
	},
	TEXTCURSORDRAWABLE("textCursorDrawable") {
		@Override
		public void apply(View view, String resName) {
			if (view instanceof EditText) {
				int textCursorRes = getResourceManager().getResIdByName(resName);
				if (textCursorRes <= 0)
					return;
				
				try {//修改光标的颜色（反射）
				      Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
				       f.setAccessible(true);
				       f.set((EditText)view, textCursorRes);
				} catch (Exception ignored) {
				   // TODO: handle exception
				}
			}
		}
	};

	String attrType;

	SkinAttrType(String attrType) {
		this.attrType = attrType;
	}

	public String getAttrType() {
		return attrType;
	}

	public abstract void apply(View view, String resName);

	public ResourceManager getResourceManager() {
		return SkinManager.getInstance().getResourceManager();
	}

}
