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
public final class CreatePlaylistUseCase_Factory implements Factory<CreatePlaylistUseCase> {
  private final Provider<PlaylistRepository> playlistRepositoryProvider;

  public CreatePlaylistUseCase_Factory(Provider<PlaylistRepository> playlistRepositoryProvider) {
    this.playlistRepositoryProvider = playlistRepositoryProvider;
  }

  @Override
  public CreatePlaylistUseCase get() {
    return newInstance(playlistRepositoryProvider.get());
  }

  public static CreatePlaylistUseCase_Factory create(
      Provider<PlaylistRepository> playlistRepositoryProvider) {
    return new CreatePlaylistUseCase_Factory(playlistRepositoryProvider);
  }

  public static CreatePlaylistUseCase newInstance(PlaylistRepository playlistRepository) {
    return new CreatePlaylistUseCase(playlistRepository);
  }
}
