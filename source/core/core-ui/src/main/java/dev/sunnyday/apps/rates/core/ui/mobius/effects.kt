package dev.sunnyday.apps.rates.core.ui.mobius

import com.spotify.mobius.rx2.RxMobius
import io.reactivex.Completable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

// region addTransformer

inline fun <reified F: RF, RF, E> RxMobius.SubtypeEffectHandlerBuilder<RF, E>.addTransformer(
    transformer: ObservableTransformer<F, E>
): RxMobius.SubtypeEffectHandlerBuilder<RF, E> =
    addTransformer(F::class.java, transformer)

inline fun <reified F, E> switch(
    noinline transform: (F) -> ObservableSource<out E>
): ObservableTransformer<F, E> = ObservableTransformer {
    it.switchMap(transform)
}

@JvmName("switchCompletable")
inline fun <reified F, reified E> switch(
    noinline transform: (F) -> Completable
): ObservableTransformer<F, E> = ObservableTransformer {
    it.switchMapCompletable(transform)
        .toObservable()
}

// endregion