package com.musicplayer.core.data.di;

import android.content.Context;
import com.musicplayer.core.data.database.MusicDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class DataModuleProviders_ProvideDatabaseFactory implements Factory<MusicDatabase> {
  private final Provider<Context> contextProvider;

  public DataModuleProviders_ProvideDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public MusicDatabase get() {
    return provideDatabase(contextProvider.get());
  }

  public static DataModuleProviders_ProvideDatabaseFactory create(
      Provider<Context> contextProvider) {
    return new DataModuleProviders_ProvideDatabaseFactory(contextProvider);
  }

  public static MusicDatabase provideDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(DataModuleProviders.INSTANCE.provideDatabase(context));
  }
}
