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
public final class GetAllPlaylistsUseCase_Factory implements Factory<GetAllPlaylistsUseCase> {
  private final Provider<PlaylistRepository> playlistRepositoryProvider;

  public GetAllPlaylistsUseCase_Factory(Provider<PlaylistRepository> playlistRepositoryProvider) {
    this.playlistRepositoryProvider = playlistRepositoryProvider;
  }

  @Override
  public GetAllPlaylistsUseCase get() {
    return newInstance(playlistRepositoryProvider.get());
  }

  public static GetAllPlaylistsUseCase_Factory create(
      Provider<PlaylistRepository> playlistRepositoryProvider) {
    return new GetAllPlaylistsUseCase_Factory(playlistRepositoryProvider);
  }

  public static GetAllPlaylistsUseCase newInstance(PlaylistRepository playlistRepository) {
    return new GetAllPlaylistsUseCase(playlistRepository);
  }
}
