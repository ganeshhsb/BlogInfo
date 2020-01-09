package com.markata.ganesh_hs.di.dagger

import javax.inject.Scope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScoped


@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class FragmentScoped