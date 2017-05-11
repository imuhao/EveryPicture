package com.imuhao.pictureeveryday.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.utils.AppUtils;
import me.drakeet.multitype.Items;
import me.drakeet.support.about.AbsAboutActivity;
import me.drakeet.support.about.Card;
import me.drakeet.support.about.Category;
import me.drakeet.support.about.Contributor;
import me.drakeet.support.about.Line;

/**
 * @author Smile
 * @time 2017/2/17  下午3:18
 * @desc ${TODD}
 */
public class AboutActivity extends AbsAboutActivity {

  public static void start(Context context) {
    Intent intent = new Intent(context, AboutActivity.class);
    context.startActivity(intent);
  }

  @Override protected void onCreateHeader(ImageView icon, TextView slogan, TextView version) {
    setHeaderContentColor(getResources().getColor(R.color.backgroundGray));
    setNavigationIcon(R.drawable.ic_back);
    icon.setImageResource(R.drawable.bbb);
    slogan.setText(getString(R.string.app_name));
    version.setText(AppUtils.getVersionName(this));
  }

  @Override protected void onItemsCreated(@NonNull Items items) {
    items.add(new Category("Developers"));
    items.add(new Contributor(R.drawable.bbb, "CaiMuhao", "Android 开发工程师"));
    items.add(new Line());

    items.add(new Category("About"));
    items.add(new Card(" LookGank 是由 CaiMuhao 开发的一款应用", "分享"));
    items.add(new Line());
  }

  @Override protected void onActionClick(View view) {
    TextView action = (TextView) view;
    if (action.getText().equals("分享")) {
      onClickShare();
    }
  }

  public void onClickShare() {
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
    intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_content));
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(Intent.createChooser(intent, getTitle()));
  }
}
