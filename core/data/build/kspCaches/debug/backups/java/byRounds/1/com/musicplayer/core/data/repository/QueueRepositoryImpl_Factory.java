package com.musicplayer.core.data.repository;

import com.musicplayer.core.data.dao.QueueDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class QueueRepositoryImpl_Factory implements Factory<QueueRepositoryImpl> {
  private final Provider<QueueDao> queueDaoProvider;

  public QueueRepositoryImpl_Factory(Provider<QueueDao> queueDaoProvider) {
    this.queueDaoProvider = queueDaoProvider;
  }

  @Override
  public QueueRepositoryImpl get() {
    return newInstance(queueDaoProvider.get());
  }

  public static QueueRepositoryImpl_Factory create(Provider<QueueDao> queueDaoProvider) {
    return new QueueRepositoryImpl_Factory(queueDaoProvider);
  }

  public static QueueRepositoryImpl newInstance(QueueDao queueDao) {
    return new QueueRepositoryImpl(queueDao);
  }
}
