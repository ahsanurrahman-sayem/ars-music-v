package com.musicplayer.core.data.repository;

import com.musicplayer.core.data.dao.TrackDao;
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
public final class TrackRepositoryImpl_Factory implements Factory<TrackRepositoryImpl> {
  private final Provider<TrackDao> trackDaoProvider;

  public TrackRepositoryImpl_Factory(Provider<TrackDao> trackDaoProvider) {
    this.trackDaoProvider = trackDaoProvider;
  }

  @Override
  public TrackRepositoryImpl get() {
    return newInstance(trackDaoProvider.get());
  }

  public static TrackRepositoryImpl_Factory create(Provider<TrackDao> trackDaoProvider) {
    return new TrackRepositoryImpl_Factory(trackDaoProvider);
  }

  public static TrackRepositoryImpl newInstance(TrackDao trackDao) {
    return new TrackRepositoryImpl(trackDao);
  }
}
