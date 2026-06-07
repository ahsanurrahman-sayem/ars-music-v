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
public final class RemoveTrackUseCase_Factory implements Factory<RemoveTrackUseCase> {
  private final Provider<TrackRepository> trackRepositoryProvider;

  public RemoveTrackUseCase_Factory(Provider<TrackRepository> trackRepositoryProvider) {
    this.trackRepositoryProvider = trackRepositoryProvider;
  }

  @Override
  public RemoveTrackUseCase get() {
    return newInstance(trackRepositoryProvider.get());
  }

  public static RemoveTrackUseCase_Factory create(
      Provider<TrackRepository> trackRepositoryProvider) {
    return new RemoveTrackUseCase_Factory(trackRepositoryProvider);
  }

  public static RemoveTrackUseCase newInstance(TrackRepository trackRepository) {
    return new RemoveTrackUseCase(trackRepository);
  }
}
