package com.musicplayer.core.domain.usecase.playlist;

import com.musicplayer.core.data.repository.PlaylistRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class AddTrackToPlaylistUseCase_Factory implements Factory<AddTrackToPlaylistUseCase> {
  private final Provider<PlaylistRepository> playlistRepositoryProvider;

  public AddTrackToPlaylistUseCase_Factory(
      Provider<PlaylistRepository> playlistRepositoryProvider) {
    this.playlistRepositoryProvider = playlistRepositoryProvider;
  }

  @Override
  public AddTrackToPlaylistUseCase get() {
    return newInstance(playlistRepositoryProvider.get());
  }

  public static AddTrackToPlaylistUseCase_Factory create(
      Provider<PlaylistRepository> playlistRepositoryProvider) {
    return new AddTrackToPlaylistUseCase_Factory(playlistRepositoryProvider);
  }

  public static AddTrackToPlaylistUseCase newInstance(PlaylistRepository playlistRepository) {
    return new AddTrackToPlaylistUseCase(playlistRepository);
  }
}
