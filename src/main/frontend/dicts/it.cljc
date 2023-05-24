(ns frontend.dicts.it
  "Provides translation to IT"
  #?(:cljs (:require [shadow.resource :as rc])))

(def ^:large-vars/data-var dicts
  {:tutorial/text #?(:cljs (rc/inline "tutorial-en.md")
                     :default "tutorial-en.md")
   :tutorial/dummy-notes #?(:cljs (rc/inline "dummy-notes-en.md")
                            :default "dummy-notes-en.md")
   :on-boarding/demo-graph "Questo è un grafo dimostrativo, le modifiche non saranno salvate finché non aprirai una cartella locale."
   :on-boarding/add-graph "Aggiungi un grafo"
   :on-boarding/open-local-dir "Apri una cartella locale"
   :on-boarding/new-graph-desc-1 "Logseq supporta sia Markdown che Org-mode. Puoi aprire una cartella esistente o crearne una nuova sul tuo dispositivo. I tuoi dati saranno salvati solo sul tuo dispositivo."
   :on-boarding/new-graph-desc-2 "Dopo che hai aperto la tua cartella, saranno create al suo interno tre cartelle:"
   :on-boarding/new-graph-desc-3 "/journals - contiene le pagine del diario giornaliero"
   :on-boarding/new-graph-desc-4 "/pages - contiene le altre pagine"
   :on-boarding/new-graph-desc-5 "/logseq - contiene i dati di configurazione, custom.css, e alcuni metadati."
   :help/start "Per iniziare"
   :help/about "Informazioni su Logseq"
   :help/roadmap "Roadmap"
   :help/bug "Segnala un problema"
   :help/feature "Richiedi una funzionalità"
   :help/changelog "Registro delle modifiche"
   :help/blog "blog di Logseq"
   :help/docs "Documentazione"
   :help/privacy "Politica sulla riservatezza"
   :help/terms "Termini di Servizio"
   :help/awesome-logseq "Awesome Logseq"
   :help/shortcuts "Scorciatoie da tastiera"
   :help/shortcuts-triggers "Attivazione delle scorciatoie"
   :help/shortcut "Scorciatoia"
   :help/slash-autocomplete "Barra di completamento automatico"
   :help/block-content-autocomplete "Autocompletamento del contenuto di blocco"
   :help/reference-autocomplete "Autocompletamente del riferimento di pagina"
   :help/block-reference "Riferimento di blocco"
   :help/open-link-in-sidebar "Apri il link nella barra laterale"

   :more "Altro"
   :search/result-for "Cerca i risultati per "
   :search/items "oggetti"
   :search/page-names "Cerca pagine per nome"
   :help/context-menu "Menu contestuale del blocco"
   :help/markdown-syntax "Sintassi Markdown"
   :help/org-mode-syntax "Sintassi Org mode"
   :bold "Grassetto"
   :italics "Corsivo"
   :highlight "Evidenzia"
   :strikethrough "Barrato"
   :code "Codice"
   :right-side-bar/help "Aiuto"
   :right-side-bar/switch-theme "Modalità tema"
   :right-side-bar/contents "Contenuti"
   :right-side-bar/page-graph "Grafico della pagina"
   :right-side-bar/block-ref "Riferimento di Blocco"
   :right-side-bar/graph-view "Vista del grafico"
   :right-side-bar/all-pages "Tutte le pagine"
   :right-side-bar/flashcards "Flashcard"
   :right-side-bar/new-page "Nuova pagina"
   :right-side-bar/show-journals "Mostra diari"
   :left-side-bar/journals "Diario"
   :left-side-bar/new-page "Nuova pagina"
   :left-side-bar/nav-favorites "Preferiti"
   :left-side-bar/nav-recent-pages "Recenti"
   :page/presentation-mode "Presentazione"
   :page/delete-confirmation "Sei sicuro di voler eliminare questa pagina e i suoi dati?"
   :page/open-in-finder "Apri nella cartella"
   :page/open-with-default-app "Apri con l'app predefinita"
   :page/make-public "Segna come pubblico per la pubblicazione"
   :page/version-history "Controlla la cronologia della pagina"
   :page/open-backup-directory "Apri la cartella dei backup delle pagine"
   :page/make-private "Segna come privato"
   :page/delete "Elimina pagina"
   :page/add-to-favorites "Aggiungi ai Preferiti"
   :page/unfavorite "Rimuovi la pagina dai Preferiti"
   :page/show-journals "Mostra diario"
   :block/name "Nome pagina"
   :page/earlier "Prima"
   :page/copy-page-url "Copia URL pagina"
   :file/name "Nome File"
   :file/last-modified-at "Ultima modifica alle"
   :file/no-data "Nessun dato"
   :file/format-not-supported "Il formato .{1} non è supportato."
   :page/created-at "Creato alle"
   :page/updated-at "Aggiornato alle"
   :page/backlinks "Collegamenti a ritroso"
   :editor/block-search "Cerca un blocco"
   :text/image "Immagine"
   :asset/confirm-delete "Sei sicuro di voler eliminare questo {1}?"
   :asset/physical-delete "Rimuovi anche i file (non possono essere ripristinati)"
   :editor/copy "Copia"
   :editor/cut "Taglia"
   :content/copy-block-ref "Copia riferimento di blocco"
   :content/copy-block-emebed "Copia blocco incorporato"
   :content/open-in-sidebar "Apri nella barra laterale"
   :content/click-to-edit "Clicca per modificare"
   :settings-page/git-confirm "Devi riavviare l'app dopo aver aggiornato le impostazioni di Git."
   :settings-page/git-switcher-label "Abilita Git auto commit"
   :settings-page/git-commit-delay "Secondi per Git auto commit"
   :settings-page/edit-config-edn "Modifica config.edn"
   :settings-page/edit-custom-css "Modifica custom.css"
   :settings-page/custom-configuration "Configurazione personalizzata"
   :settings-page/custom-theme "Tema personalizzato"
   :settings-page/show-brackets "Mostra parentesi"
   :settings-page/spell-checker "Correttore ortografico"
   :settings-page/auto-updater "Aggiornamento automatico"
   :settings-page/disable-sentry "Invia dati di utilizzo e diagnostica a Logseq"
   :settings-page/preferred-outdenting "Indentamento logico"
   :settings-page/custom-date-format "Formato data preferito"
   :settings-page/preferred-file-format "Formato file preferito"
   :settings-page/preferred-workflow "Flusso di lavoro preferito"
   :settings-page/enable-shortcut-tooltip "Abilita suggerimenti scorciatoie"
   :settings-page/enable-timetracking "Tracciamento del tempo"
   :settings-page/enable-tooltip "Suggerimenti"
   :settings-page/enable-journals "Diario"
   :settings-page/enable-all-pages-public "Tutte le pagine pubbliche durante la pubblicazione"
   :settings-page/customize-shortcuts "Scorciatoie da tastiera"
   :settings-page/shortcut-settings "Personalizza scorciatoie"
   :settings-page/home-default-page "Imposta la home page predefinita"
   :settings-page/enable-block-time "Indicatori temporali sui blocchi"
   :settings-page/clear-cache "Pulisci cache"
   :settings-page/clear "Pulisci"
   :settings-page/developer-mode "Modalità sviluppatore"
   :settings-page/developer-mode-desc "La modalità sviluppatore aiuta i contributori e gli sviluppatori di estensioni a testare le loro integrazioni con Logseq in modo più efficiente."
   :settings-page/current-version "Versione attuale"
   :settings-page/tab-general "Generale"
   :settings-page/tab-editor "Editor"
   :settings-page/tab-version-control "Controllo di versione"
   :settings-page/tab-advanced "Avanzate"
   :settings-page/plugin-system "Sistema di plugin"
   :settings-page/network-proxy "Proxy di rete"
   :yes "Sì"
   :submit "Invia"
   :cancel "Annulla"
   :close "Chiudi"
   :delete "Elimina"
   :save "Salva"
   :type "Tipo"
   :host "Host"
   :port "Porta"
   :re-index "Re-indicizza"
   :re-index-detail "Ricostruisci il grafo"
   :re-index-multiple-windows-warning "È necessario chiudere le altre finestre prima di reindicizzare questo grafo."
   :re-index-discard-unsaved-changes-warning "La reindicizzazione elimina il grafo corrente, quindi elabora nuovamente tutti i file poiché sono attualmente archiviati su disco. Perderai le modifiche non salvate e potrebbe volerci del tempo. Continuare?"
   :open-new-window "Nuova finestra"
   :sync-from-local-files "Ricarica"
   :sync-from-local-files-detail "Importa cambiamenti da un file locale"
   :sync-from-local-changes-detected "Il ricaricamento rileva ed elabora i file modificati sul disco e divergenti dal contenuto effettivo della pagina Logseq. Continuare?"

   :search/publishing "Cerca"
   :search "Cerca o crea una pagina"
   :page-search "Cerca nella pagina corrente"
   :graph-search "Cerca nel grafo"
   :new-page "Nuova pagina"
   :new-graph "Aggiungi nuovo grafo"
   :graph "Grafo"
   :graph/persist "Logseq sta sincronizzando lo stato interno, per favore attendi alcuni secondi."
   :graph/persist-error "Sincronizzazione dello stato interno fallita."
   :graph/save "Salvataggio..."
   :graph/save-success "Salvato con successo"
   :graph/save-error "Salvataggio fallito"
   :export "Esporta"
   :export-graph "Esporta grafo"
   :export-page "Esporta pagina"
   :export-markdown "Esporta come Markdown standard (senza le proprietà dei blocchi)"
   :export-opml "Esporta come OPML"
   :export-public-pages "Esporta le pagine pubbliche"
   :export-json "Esporta come JSON"
   :export-roam-json "Esporta come Roam JSON"
   :export-edn "Esporta come EDN"
   :all-graphs "Tutti i grafi"
   :all-pages "Tutte le pagine"
   :all-files "Tutti i file"
   :remove-orphaned-pages "Rimuovi pagine orfane"
   :all-journals "Tutte le pagine di diario"
   :settings "Impostazioni"
   :settings-of-plugins "Impostazioni plugin"
   :plugins "Plugin"
   :themes "Temi"
   :relaunch-confirm-to-work "È necessario riavviare l'app per farla funzionare. Vuoi riavviarla ora?"
   :import "Importa"
   :join-community "Unisciti alla comunità"
   :help-shortcut-title "Clicca per conoscere le scorciatoie e altri suggerimenti"
   :loading "Caricamento"
   :parsing-files "Analisi dei file"
   :loading-files "Caricamento dei file"
   :login "Accedi"
   :logout "Esci"
   :download "Scarica"
   :language "Lingua"
   :remove-background "Rimuovi lo sfondo"
   :open-a-directory "Apri una cartella locale"

   :help/shortcut-page-title "Scorciatoie da tastiera"

   :plugin/installed "Installato"
   :plugin/not-installed "Non installato"
   :plugin/installing "Installazione"
   :plugin/install "Installa"
   :plugin/reload "Ricarica"
   :plugin/update "Aggiorna"
   :plugin/check-update "Controlla aggiornamenti"
   :plugin/check-all-updates "Controlla tutti gli aggiornamenti"
   :plugin/refresh-lists "Ricarica lista"
   :plugin/enabled "Abilitato"
   :plugin/disabled "Disabilitato"
   :plugin/update-available "Aggiornamento disponibile"
   :plugin/updating "Aggiornamento"
   :plugin/uninstall "Disinstalla"
   :plugin/marketplace "Libreria"
   :plugin/downloads "Numero di scaricamenti"
   :plugin/stars "Stelle"
   :plugin/title "Titolo"
   :plugin/all "Tutti"
   :plugin/unpacked "Non pacchettizzati"
   :plugin/delete-alert "Sei sicuro di voler disinstallare [{1}]?"
   :plugin/open-settings "Apri impostazioni"
   :plugin/open-package "Apri pacchetto"
   :plugin/load-unpacked "Carica plugin non pacchettizzato"
   :plugin/restart "Riavvia app"
   :plugin/unpacked-tips "Seleziona la cartella del plugin"
   :plugin/contribute "✨ Sviluppa e sottoponici un nuovo plugin"
   :plugin/up-to-date "È aggiornato"
   :plugin/custom-js-alert "Trovato il file custom.js, è consentito eseguirlo? (Se non si comprende il contenuto di questo file, si consiglia di non consentire l'esecuzione, che presenta alcuni rischi per la sicurezza.)"

   :pdf/copy-ref "Copia riferimenti"
   :pdf/copy-text "Copia testo"
   :pdf/linked-ref "Riferimenti collegati"
   :pdf/toggle-dashed "Stile tratteggiato per evidenziare l'area"

   :updater/new-version-install "Una nuova versione è stata scaricata."
   :updater/quit-and-install "Riavvia per installarla"

   :paginates/pages "Totale {1} pagine"
   :paginates/prev "Precedente"
   :paginates/next "Successivo"

   :tips/all-done "Completato"

   :command-palette/prompt "Digita un comando"
   :select/default-prompt "Selezionane uno"
   :select.graph/prompt "Seleziona un grafo"
   :select.graph/empty-placeholder-description "Non ci sono grafi corrispondenti. Vuoi aggiungerne uno nuovo?"
   :select.graph/add-graph "Sì, aggiungi un nuovo grafo"

   :file-sync/other-user-graph "Il grafo locale attuale è associato al grafo remoto di un altro utente. Non è quindi possibile avviare la sincronizzazione."
   :file-sync/graph-deleted "Il grafo attuale è stato eliminato"
   :settings-page/edit-export-css "Modificare export.css"
   :settings-page/enable-flashcards "Flashcard"
   :settings-page/export-theme "Esporta tema"

   :command.date-picker/complete         "Selettore data: scegli il giorno selezionato"
   :command.date-picker/prev-day         "Selettore data: Seleziona il giorno precedente"
   :command.date-picker/next-day         "Selettore data: Seleziona il giorno successivo"
   :command.date-picker/prev-week        "Selettore data: Seleziona la settimana precedente"
   :command.date-picker/next-week        "Selettore data: Seleziona la settimana successiva"
   :command.pdf/previous-page            "Pagina precedente del pdf corrente"
   :command.pdf/next-page                "Pagina successiva del pdf corrente"
   :command.auto-complete/complete       "Auto completamento: Scegli l'oggetto selezionato"
   :command.auto-complete/prev           "Auto completamento: Seleziona l'oggetto precedente"
   :command.auto-complete/next           "Auto completamento: Seleziona l'oggetto successivo"
   :command.auto-complete/shift-complete "Auto completamento: Apri l'oggetto selezionato nella barra laterale"
   :command.auto-complete/open-link      "Auto completamento: Apri l'oggetto selezionato nel browser"
   :command.cards/toggle-answers         "Carte: mostra/nascondi risposte/chiusure"
   :command.cards/next-card              "Carte: prossima carta"
   :command.cards/forgotten              "Carte: dimenticato"
   :command.cards/remembered             "Carte: ricordato"
   :command.cards/recall                 "Carte: ci ho messo un pò a ricordarlo"
   :command.editor/escape-editing        "Esci dalla modifica"
   :command.editor/backspace             "Tasto Backspace / Cancella all'indietro"
   :command.editor/delete                "Tasto Delete / Cancella avanti"
   :command.editor/new-block             "Crea un nuovo blocco"
   :command.editor/new-line              "Nuova riga accapo nel blocco attuale"
   :command.editor/follow-link           "Segui il link sotto al cursore"
   :command.editor/open-link-in-sidebar  "Apri il link nella barra laterale"
   :command.editor/bold                  "Grassetto"
   :command.editor/italics               "Corsivo"
   :command.editor/highlight             "Evidenzia"
   :command.editor/strikethrough         "Barrato"
   :command.editor/clear-block           "Elimina l'intero contenuto del blocco"
   :command.editor/kill-line-before      "Cancella la riga prima della posizione del cursore"
   :command.editor/kill-line-after       "Cancella la riga dopo la posizione del cursore"
   :command.editor/beginning-of-block    "Muovi il cursore all'inizio di un blocco"
   :command.editor/end-of-block          "Muovi il cursore alla fine di un blocco"
   :command.editor/forward-word          "Muovi il cursore in avanti di una parola"
   :command.editor/backward-word         "Muovi il cursore all'indietro di una parola"
   :command.editor/forward-kill-word     "Elimina una parola in avanti"
   :command.editor/backward-kill-word    "Elimina una parola all'indietro"
   :command.editor/replace-block-reference-at-point "Sostituisci il riferimento di blocco con il suo contenuto al punto"
   :command.editor/paste-text-in-one-block-at-point "Incolla testo in un blocco al punto"
   :command.editor/insert-youtube-timestamp         "Inserisci marca temporale di youtube"
   :command.editor/cycle-todo              "Cicla lo stato TODO dell'elemento corrente"
   :command.editor/up                      "Muovi il cursore sopra / Seleziona sopra"
   :command.editor/down                    "Muovi il cursore sotto / Seleziona sotto"
   :command.editor/left                    "Muovi il cursore a sinistra / Apri il blocco selezionato all'inizio"
   :command.editor/right                   "Muovi il cursore a destra / Apri il blocco selezionato all'inizio"
   :command.editor/select-up               "Seleziona il contenuto sopra"
   :command.editor/select-down             "Seleziona il contenuto sotto"
   :command.editor/move-block-up           "Muovi il blocco sopra"
   :command.editor/move-block-down         "Muovi il blocco sotto"
   :command.editor/open-edit               "Modifica il blocco selezionato"
   :command.editor/select-block-up         "Seleziona blocco sopra"
   :command.editor/select-block-down       "Seleziona blocco sotto"
   :command.editor/delete-selection        "Elimina i blocchi selezionati"
   :command.editor/expand-block-children   "Espandi"
   :command.editor/collapse-block-children "Collassa"
   :command.editor/indent                  "Rientra blocco"
   :command.editor/outdent                 "Annulla il rientro blocco"
   :command.editor/copy                    "Copia (copia una selezione o un riferimento di blocco)"
   :command.editor/cut                     "Taglia"
   :command.editor/undo                    "Annulla"
   :command.editor/redo                    "Rifai"
   :command.editor/insert-link             "Link HTML"
   :command.editor/select-all-blocks       "Seleziona tutti i blocchi"
   :command.editor/zoom-in                 "Ingrandisci blocco di modifica / Avanti altrimenti"
   :command.editor/zoom-out                "Rimpicciolisci il blocco di modifica / Indietro altrimenti"
   :command.ui/toggle-brackets             "Selezionare se visualizzare le parentesi"
   :command.go/search-in-page              "Cerca nella pagina attuale"
   :command.go/search                      "Ricerca testo completo"
   :command.go/journals                    "Vai ai diari"
   :command.go/backward                    "Indietro"
   :command.go/forward                     "Avanti"
   :command.search/re-index                "Ricostruisci indice di ricerca"
   :command.sidebar/open-today-page        "Apri la pagina di oggi nella barra laterale destra"
   :command.sidebar/clear                  "Pulisci tutto nella barra laterale destra"
   :command.misc/copy                      "mod+c"
   :command.command-palette/toggle         "Attiva/disattiva tavolozza comandi"
   :command.graph/open                     "Seleziona il diagramma da aprire"
   :command.graph/remove                   "Rimuovi un diagramma"
   :command.graph/add                      "Aggiungi un diagramma"
   :command.graph/save                     "Salva il diagramma corrente su disco"
   :command.command/run                    "Esegui comando git"
   :command.go/home                        "Vai all'inizio"
   :command.go/all-pages                   "Vai a tutte le pagine"
   :command.go/graph-view                  "Vai alla visualizzazione diagramma"
   :command.go/keyboard-shortcuts          "Vai alle scorciatoie da tastiera"
   :command.go/tomorrow                    "Vai a domani"
   :command.go/next-journal                "Vai al prossimo diario"
   :command.go/prev-journal                "Vai al diario precedente"
   :command.go/flashcards                  "Attiva/disattiva carte flash"
   :command.ui/toggle-document-mode        "Attiva/disattiva modalità documento"
   :command.ui/toggle-settings             "Attiva/disattiva impostazioni"
   :command.ui/toggle-right-sidebar        "Attiva/disattiva barra laterale destra"
   :command.ui/toggle-left-sidebar         "Attiva/disattiva barra laterale sinistra"
   :command.ui/toggle-help                 "Attiva/disattiva aiuto"
   :command.ui/toggle-theme                "Passa dal tema scuro a quello chiaro"
   :command.ui/toggle-contents             "Attiva/disattiva i contenuti nella barra laterale"
             ;;  :command.ui/open-new-window             "Apri un'altra finestra"
   :command.command/toggle-favorite        "Aggiungi a/rimuovi dai preferiti"
   :command.editor/open-file-in-default-app "Apri file nell'app predefinita"
   :command.editor/open-file-in-directory   "Apri file nella directory principale"
   :command.editor/copy-current-file        "Copia file corrente"
   :command.ui/toggle-wide-mode             "Attiva/disattiva modalità ampia"
   :command.ui/select-theme-color           "Seleziona i colori del tema disponibili"
   :command.ui/goto-plugins                 "Vai alla dashboard dei plugin"
   :command.editor/toggle-open-blocks       "Attiva/disattiva i blocchi aperti (comprimi o espandi tutti i blocchi)"
   :command.ui/toggle-cards                 "Attiva/disattiva le carte"
   :command.git/commit                      "Git messaggio di commit"
   :shortcut.category/basics                "Nozioni di base"
   :shortcut.category/formatting            "Formattazione"
   :shortcut.category/navigating            "Navigazione"
   :shortcut.category/block-editing         "Modiifica blocco generale"
   :shortcut.category/block-command-editing "Modifica comandi blocco"
   :shortcut.category/block-selection       "Selezione blocco (premi Esc per uscire dalla selezione)"
   :shortcut.category/toggle                "Attiva/disattiva"
   :shortcut.category/others                "Altri"
   :command.editor/copy-embed               "Copia un incorporamento di blocco che punta al blocco corrente"
   :command.editor/copy-text                "Copia le selezioni come testo"
   :command.pdf/close                       "Chiudi anteprima PDF"})