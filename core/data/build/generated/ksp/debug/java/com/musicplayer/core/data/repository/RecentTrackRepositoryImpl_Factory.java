package com.musicplayer.core.data.repository;

import com.musicplayer.core.data.dao.RecentTrackDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class RecentTrackRepositoryImpl_Factory implements Factory<RecentTrackRepositoryImpl> {
  private final Provider<RecentTrackDao> recentTrackDaoProvider;

  public RecentTrackRepositoryImpl_Factory(Provider<RecentTrackDao> recentTrackDaoProvider) {
    this.recentTrackDaoProvider = recentTrackDaoProvider;
  }

  @Override
  public RecentTrackRepositoryImpl get() {
    return newInstance(recentTrackDaoProvider.get());
  }

  public static RecentTrackRepositoryImpl_Factory create(
      Provider<RecentTrackDao> recentTrackDaoProvider) {
    return new RecentTrackRepositoryImpl_Factory(recentTrackDaoProvider);
  }

  public static RecentTrackRepositoryImpl newInstance(RecentTrackDao recentTrackDao) {
    return new RecentTrackRepositoryImpl(recentTrackDao);
  }
}
