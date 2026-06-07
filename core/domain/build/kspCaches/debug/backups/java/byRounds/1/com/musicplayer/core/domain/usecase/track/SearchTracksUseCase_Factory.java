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
public final class SearchTracksUseCase_Factory implements Factory<SearchTracksUseCase> {
  private final Provider<TrackRepository> trackRepositoryProvider;

  public SearchTracksUseCase_Factory(Provider<TrackRepository> trackRepositoryProvider) {
    this.trackRepositoryProvider = trackRepositoryProvider;
  }

  @Override
  public SearchTracksUseCase get() {
    return newInstance(trackRepositoryProvider.get());
  }

  public static SearchTracksUseCase_Factory create(
      Provider<TrackRepository> trackRepositoryProvider) {
    return new SearchTracksUseCase_Factory(trackRepositoryProvider);
  }

  public static SearchTracksUseCase newInstance(TrackRepository trackRepository) {
    return new SearchTracksUseCase(trackRepository);
  }
}
