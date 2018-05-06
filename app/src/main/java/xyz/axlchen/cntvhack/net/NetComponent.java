package xyz.axlchen.cntvhack.net;

import javax.inject.Singleton;

import dagger.Component;
import xyz.axlchen.cntvhack.activitys.BaseActivity;
import xyz.axlchen.cntvhack.fragments.BaseFragment;

@Singleton
@Component(modules = {NetModule.class})
public interface NetComponent {
    void inject(BaseFragment fragment);
    void inject(BaseActivity activity);
}
