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
public final class GetPlaylistTracksUseCase_Factory implements Factory<GetPlaylistTracksUseCase> {
  private final Provider<PlaylistRepository> playlistRepositoryProvider;

  public GetPlaylistTracksUseCase_Factory(Provider<PlaylistRepository> playlistRepositoryProvider) {
    this.playlistRepositoryProvider = playlistRepositoryProvider;
  }

  @Override
  public GetPlaylistTracksUseCase get() {
    return newInstance(playlistRepositoryProvider.get());
  }

  public static GetPlaylistTracksUseCase_Factory create(
      Provider<PlaylistRepository> playlistRepositoryProvider) {
    return new GetPlaylistTracksUseCase_Factory(playlistRepositoryProvider);
  }

  public static GetPlaylistTracksUseCase newInstance(PlaylistRepository playlistRepository) {
    return new GetPlaylistTracksUseCase(playlistRepository);
  }
}
