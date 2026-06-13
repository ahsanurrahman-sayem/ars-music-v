package com.musicplayer.core.data.di;

import com.musicplayer.core.data.dao.PlaylistDao;
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
public final class DataModuleProviders_ProvidePlaylistDaoFactory implements Factory<PlaylistDao> {
  private final Provider<MusicDatabase> databaseProvider;

  public DataModuleProviders_ProvidePlaylistDaoFactory(Provider<MusicDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public PlaylistDao get() {
    return providePlaylistDao(databaseProvider.get());
  }

  public static DataModuleProviders_ProvidePlaylistDaoFactory create(
      Provider<MusicDatabase> databaseProvider) {
    return new DataModuleProviders_ProvidePlaylistDaoFactory(databaseProvider);
  }

  public static PlaylistDao providePlaylistDao(MusicDatabase database) {
    return Preconditions.checkNotNullFromProvides(DataModuleProviders.INSTANCE.providePlaylistDao(database));
  }
}
