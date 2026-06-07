package com.musicplayer.core.domain.usecase.track;

import com.musicplayer.core.data.repository.TrackRepository;
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
public final class GetFavoriteTracksUseCase_Factory implements Factory<GetFavoriteTracksUseCase> {
  private final Provider<TrackRepository> trackRepositoryProvider;

  public GetFavoriteTracksUseCase_Factory(Provider<TrackRepository> trackRepositoryProvider) {
    this.trackRepositoryProvider = trackRepositoryProvider;
  }

  @Override
  public GetFavoriteTracksUseCase get() {
    return newInstance(trackRepositoryProvider.get());
  }

  public static GetFavoriteTracksUseCase_Factory create(
      Provider<TrackRepository> trackRepositoryProvider) {
    return new GetFavoriteTracksUseCase_Factory(trackRepositoryProvider);
  }

  public static GetFavoriteTracksUseCase newInstance(TrackRepository trackRepository) {
    return new GetFavoriteTracksUseCase(trackRepository);
  }
}
