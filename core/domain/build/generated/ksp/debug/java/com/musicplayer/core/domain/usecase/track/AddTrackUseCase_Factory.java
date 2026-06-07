package com.musicplayer.core.domain.usecase.track;

import android.content.Context;
import com.musicplayer.core.data.repository.TrackRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AddTrackUseCase_Factory implements Factory<AddTrackUseCase> {
  private final Provider<TrackRepository> trackRepositoryProvider;

  private final Provider<Context> contextProvider;

  public AddTrackUseCase_Factory(Provider<TrackRepository> trackRepositoryProvider,
      Provider<Context> contextProvider) {
    this.trackRepositoryProvider = trackRepositoryProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public AddTrackUseCase get() {
    return newInstance(trackRepositoryProvider.get(), contextProvider.get());
  }

  public static AddTrackUseCase_Factory create(Provider<TrackRepository> trackRepositoryProvider,
      Provider<Context> contextProvider) {
    return new AddTrackUseCase_Factory(trackRepositoryProvider, contextProvider);
  }

  public static AddTrackUseCase newInstance(TrackRepository trackRepository, Context context) {
    return new AddTrackUseCase(trackRepository, context);
  }
}
