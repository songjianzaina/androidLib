package com.insworks.lib_base.base;

public abstract class ButterActivity extends BaseActivity {
//    protected Unbinder bind;
    @Override
    protected void contentViewfilled() {
//        bind = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (bind != null) {
//            bind.unbind();
//        }
    }
}
