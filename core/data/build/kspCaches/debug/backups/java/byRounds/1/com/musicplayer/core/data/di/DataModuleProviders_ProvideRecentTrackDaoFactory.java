package com.musicplayer.core.data.di;

import com.musicplayer.core.data.dao.RecentTrackDao;
import com.musicplayer.core.data.database.MusicDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DataModuleProviders_ProvideRecentTrackDaoFactory implements Factory<RecentTrackDao> {
  private final Provider<MusicDatabase> databaseProvider;

  public DataModuleProviders_ProvideRecentTrackDaoFactory(
      Provider<MusicDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public RecentTrackDao get() {
    return provideRecentTrackDao(databaseProvider.get());
  }

  public static DataModuleProviders_ProvideRecentTrackDaoFactory create(
      Provider<MusicDatabase> databaseProvider) {
    return new DataModuleProviders_ProvideRecentTrackDaoFactory(databaseProvider);
  }

  public static RecentTrackDao provideRecentTrackDao(MusicDatabase database) {
    return Preconditions.checkNotNullFromProvides(DataModuleProviders.INSTANCE.provideRecentTrackDao(database));
  }
}
