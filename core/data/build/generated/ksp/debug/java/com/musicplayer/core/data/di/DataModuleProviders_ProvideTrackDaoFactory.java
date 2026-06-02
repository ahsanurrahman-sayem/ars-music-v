package com.musicplayer.core.data.di;

import com.musicplayer.core.data.dao.TrackDao;
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
public final class DataModuleProviders_ProvideTrackDaoFactory implements Factory<TrackDao> {
  private final Provider<MusicDatabase> databaseProvider;

  public DataModuleProviders_ProvideTrackDaoFactory(Provider<MusicDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public TrackDao get() {
    return provideTrackDao(databaseProvider.get());
  }

  public static DataModuleProviders_ProvideTrackDaoFactory create(
      Provider<MusicDatabase> databaseProvider) {
    return new DataModuleProviders_ProvideTrackDaoFactory(databaseProvider);
  }

  public static TrackDao provideTrackDao(MusicDatabase database) {
    return Preconditions.checkNotNullFromProvides(DataModuleProviders.INSTANCE.provideTrackDao(database));
  }
}
