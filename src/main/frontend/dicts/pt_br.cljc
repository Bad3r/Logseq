(ns frontend.dicts.pt-br
  "Provides translation to pt-BR"
  #?(:cljs (:require [shadow.resource :as rc])))

(def ^:large-vars/data-var dicts
  {:on-boarding/demo-graph "Esse é um grafo de demonstração, mudanças não serão salvas enquanto uma pasta local não for aberta."
   :on-boarding/add-graph "Adicionar grafo"
   :on-boarding/open-local-dir "Abrir pasta local"
   :on-boarding/new-graph-desc-1 "Logseq funciona com Markdown e Org-mode. Você pode abrir uma pasta existente ou criar uma nova em seu dispositivo. Seus dados serão armazenados apenas neste dispositivo."
   :on-boarding/new-graph-desc-2 "Após abrir sua pasta, três pastas serão criadas nela:"
   :on-boarding/new-graph-desc-3 "/journals - armazena suas páginas diárias"
   :on-boarding/new-graph-desc-4 "/pages - armazena as outras páginas"
   :on-boarding/new-graph-desc-5 "/logseq - armazena configuração, custom.css e metadados."
   :help/start "Começar a usar"
   :help/about "Sobre o Logseq"
   :help/roadmap "Plano de implementação"
   :help/bug "Relato de erros"
   :help/feature "Pedido de funcionalidades"
   :help/changelog "Registo de alterações"
   :help/blog "Blog do Logseq"
   :help/docs "Documentação"
   :help/privacy "Política de privacidade"
   :help/terms "Termos"
   :help/awesome-logseq "Incrível Logseq"
   :help/shortcuts "Atalhos de Teclado"
   :help/shortcuts-triggers "Gatilhos"
   :help/shortcut "Atalho"
   :help/slash-autocomplete "Auto-completar com a barra"
   :help/block-content-autocomplete "Auto-completar conteúdo de bloco (Src, Quote, Query, etc)"
   :help/reference-autocomplete "Auto-completar referência de páginas"
   :help/block-reference "Referência de bloco"
   :more "Mais"
   :search/result-for "Resultado da pesquisa para "
   :help/context-menu "Menu contextual"
   :help/markdown-syntax "Sintaxe de Markdown"
   :help/org-mode-syntax "Sintaxe de modo Org"
   :bold "Negrito"
   :italics "Itálico"
   :highlight "Realçado"
   :strikethrough "Rasurado"
   :code "Código"
   :right-side-bar/help "Ajuda"
   :right-side-bar/switch-theme "Temas"
   :right-side-bar/contents "Conteúdo"
   :right-side-bar/page-graph "Grafo da página"
   :right-side-bar/block-ref "Referência de bloco"
   :right-side-bar/flashcards "Flashcards"
   :right-side-bar/new-page "Nova página"
   :left-side-bar/journals "Diários"
   :page/presentation-mode "Modo de apresentação"
   :page/delete-confirmation "Tem certeza que quer apagar esta página e o arquivo associado?"
   :page/open-in-finder "Abrir em pasta"
   :page/open-with-default-app "Abrir com a aplicação por omissão"
   :page/make-public "Tornar pública para publicação"
   :page/version-history "Ver histórico da página"
   :page/make-private "Tornar privada"
   :page/delete "Excluir página"
   :page/add-to-favorites "Adicionar aos Favoritos"
   :page/show-journals "Mostrar Diários"
   :block/name "Nome da página"
   :page/earlier "Antes"
   :file/name "Nome do arquivo"
   :file/last-modified-at "Última modificação em"
   :file/no-data "Sem dados"
   :file/format-not-supported "Formato .{1} não suportado."
   :page/created-at "Criado em"
   :page/updated-at "Atualizado em"
   :page/backlinks "Back Links"
   :editor/block-search "Pesquisar por um bloco"
   :text/image "Imagem"
   :asset/confirm-delete "Tem certeza que quer excluir este {1}?"
   :asset/physical-delete "Remover também o arquivo (não poderá ser restaurado)"
   :editor/copy "Copiar"
   :editor/cut "Cortar"
   :content/copy-block-ref "Copiar referência do bloco"
   :content/open-in-sidebar "Abrir na barra lateral"
   :content/click-to-edit "Clicar para editar"
   :settings-page/edit-config-edn "Editar config.edn para o repositório atual"
   :settings-page/git-confirm "É necessário reiniciar a aplicação após atualizar as configurações do Git."
   :settings-page/git-switcher-label "Habilitar auto-consolidação no Git"
   :settings-page/git-commit-delay "Intervalo em segundos para auto-consolidação"
   :settings-page/show-brackets "Mostrar parênteses retos"
   :settings-page/spell-checker "Verificador ortográfico"
   :settings-page/disable-sentry "Enviar dados de utilização e diagnósticos para Logseq"
   :settings-page/preferred-outdenting "Ativar dedentação lógica"
   :settings-page/auto-expand-block-refs "Expandir as referências de bloco automaticamente ao aumentar o zoom"
   :settings-page/custom-date-format "Formato de data preferido"
   :settings-page/preferred-file-format "Formato de Arquivo preferido"
   :settings-page/preferred-workflow "Fluxo de trabalho preferido"
   :settings-page/enable-timetracking "Ativar controle de tempo"
   :settings-page/enable-tooltip "Ativar dicas de ferramentas"
   :settings-page/enable-journals "Ativar diários"
   :settings-page/enable-all-pages-public "Ativar todas as páginas públicas ao publicar"
   :settings-page/customize-shortcuts "Atalhos de teclado"
   :settings-page/shortcut-settings "Personalizar atalhos"
   :settings-page/home-default-page "Definir a página inicial padrão"
   :settings-page/enable-block-time "Ativar carimbos temporais nos blocos"
   :settings-page/clear-cache "Limpar cache"
   :settings-page/clear "Limpar"
   :settings-page/developer-mode "Modo de desenvolvimento"
   :settings-page/developer-mode-desc "O modo de desenvolvimento ajuda os contribuidores e programadores de extensões a testar as suas integrações com o Logseq de forma eficiente."
   :settings-page/current-version "Versão atual"
   :settings-page/tab-general "Geral"
   :settings-page/tab-editor "Editor"
   :settings-page/tab-advanced "Avançado"
   :settings-page/tab-version-control "Controle de Versões"
   :yes "Sim"
   :submit "Submeter"
   :cancel "Cancelar"
   :close "Fechar"
   :delete "Excluir"
   :re-index "Re-indexar"
   :search/publishing "Pesquisar"
   :search "Pesquisar ou Criar Página"
   :page-search "Pesquisar na página atual"
   :graph-search "Pesquisar grafo"
   :new-page "Nova página"
   :new-graph "Adicionar novo grafo"
   :graph "Grafo"
   :export "Exportar"
   :export-graph "Exportar Grafo"
   :export-markdown "Exportar como Markdown padrão (sem propriedades de bloco)"
   :export-opml "Exportar como OPML"
   :export-page "Exportar página"
   :export-public-pages "Exportar páginas públicas"
   :export-json "Exportar como JSON"
   :export-roam-json "Exportar como Roam JSON"
   :export-edn "Exportar como EDN"
   :all-graphs "Todos os grafos"
   :all-pages "Todas as páginas"
   :all-files "Todos os arquivos"
   :all-journals "Todos os diários"
   :settings "Configurações"
   :plugins "Plugins"
   :themes "Temas"
   :relaunch-confirm-to-work "Deve reiniciar a aplicação para fazê-lo funcionar. Quer reiniciar agora?"
   :import "Importar"
   :join-community "Junte-se à comunidade"
   :help-shortcut-title "Clique para ver atalhos e outras dicas"
   :loading "Carregando"
   :parsing-files "Analisando arquivos"
   :loading-files "Carregando arquivos"
   :login "Iniciar sessão"
   :logout "Sessão final"
   :download "Baixar"
   :language "Idioma"
   :remove-background "Remover fundo"
   :re-index-detail "Re-indexar grafo"
   :open-a-directory "Abrir uma pasta local"
   :open-new-window "Nova janela"

   :help/shortcut-page-title "Atalhos de teclado"

   :pdf/copy-ref "Copiar referência"
   :pdf/copy-text "Copiar texto"
   :pdf/linked-ref "Referências ligadas"
   :command-palette/prompt "Digite um comando"
   :remove-orphaned-pages "Remover páginas órfãs"
   :sync-from-local-files "Recarregar arquivos"
   :sync-from-local-files-detail "Importar modificações de arquivos"
   :content/copy-block-emebed "Copiar bloco para incorporar"
   :left-side-bar/nav-favorites "Favoritos"
   :left-side-bar/nav-recent-pages "Recentes"
   :left-side-bar/new-page "Nova página"
   :page/unfavorite "Tirar página dos favoritos"
   :paginates/next "Próximo"
   :paginates/prev "Anterior"
   :pdf/toggle-dashed "Destaque estilo pontilhado"
   :plugin/all "Todos"
   :plugin/check-all-updates "Verificar atualizações"
   :plugin/check-update "Verificar atualização"
   :plugin/contribute "✨ Crie e envie um novo plugin"
   :plugin/custom-js-alert "Arquivo custom.js encontrado, deseja habilitar sua execução? (A execução deste arquivo pode trazer riscos, se você não conhece o conteúdo do arquivo é recomendado que você não ative esta opção.)"

   :plugin/delete-alert "Certeza que deseja excluir o plugin? [{1}]?"
   :plugin/disabled "Desabilitado"
   :plugin/downloads "Baixados"
   :plugin/enabled "Habilitado"
   :plugin/install "Instalar"
   :plugin/installed "Instalado"
   :plugin/installing "Instalando"
   :plugin/load-unpacked "Carregar plugin descompactado"
   :plugin/marketplace "Loja"
   :plugin/open-settings "Abrir configurações"
   :plugin/refresh-lists "Recarregar lista"
   :plugin/restart "Reiniciar o App"
   :plugin/title "Título"
   :plugin/uninstall "Desinstalar"
   :plugin/unpacked-tips "Selecione a pasta do plugin"
   :plugin/up-to-date "Está atualizado"
   :plugin/update "Atualizar"
   :plugin/update-available "Atualização disponível"
   :plugin/updating "Atualizando"
   :right-side-bar/all-pages "Todas as páginas"
   :right-side-bar/graph-view "Ver grafo"
   :search/page-names "Procurar nome da página"
   :plugin/stars "Estrelas"
   :select/default-prompt "Selecione um"
   :select.graph/prompt "Selecione um grafo"
   :select.graph/add-graph "Sim, adicionar outro grafo"
   :select.graph/empty-placeholder-description "Nenhum grafo encontrado. Deseja adicionar um novo?"
   :settings-page/enable-shortcut-tooltip "Habilitar dicas de atalho"
   :tips/all-done "Tudo certo"
   :updater/new-version-install "Uma nova versão foi baixada"
   :updater/quit-and-install "Reinicie para instalar"
   :paginates/pages "Total de {1} paginas"
   :plugin/open-package "Abrir pacote"
   :plugin/reload "Reiniciar"
   :plugin/unpacked "Descompactado"
   :settings-page/auto-updater "Auto atualizar"
   :settings-page/custom-configuration "Configuração personalizada"
   :settings-page/custom-theme "Tema personalizado"
   :settings-page/edit-custom-css "Editar custom.css"
   :re-index-multiple-windows-warning "Você precisa fechar as outras janelas antes de reindexar este grafo"
   :re-index-discard-unsaved-changes-warning "A reindexação descartará o grafo atual e processará todos os arquivos novamente conforme estão armazenados no disco. Você perderá as alterações não salvas e pode demorar um pouco. Continuar?"
   :sync-from-local-changes-detected "Atualizar detecta e processa arquivos modificados em seu disco e que são diferentes do conteúdo atual da página do Logseq. Continuar?"
   :page/open-backup-directory "Abra a listagem de backups de página"
   :save "Salvar"
   :type "Tipo"
   :host "Host"
   :port "Porta"
   :settings-of-plugins "Configurações de Plugin"
   :graph/persist "O Logseq está sincronizando seu status interno, aguarde alguns segundos."
   :graph/persist-error "Falha na sincronização do status interno."
   :graph/save "Salvando..."
   :graph/save-success "Salvo com sucesso"
   :graph/save-error "Falha ao salvar"
   :settings-page/plugin-system "Sistema de Plugins"
   :settings-page/network-proxy "Proxy de Rede"

   :file-sync/other-user-graph "O grafo local atual está ligado ao grafo remoto de outro usuário. Portanto, não consigo iniciar a sincronização."
   :file-sync/graph-deleted "O grafo remoto atual foi excluído"

   :page/copy-page-url "Copiar URL da página"
   :plugin/not-installed "Não instalado"
   :tutorial/dummy-notes #?(:cljs (rc/inline "dummy-notes-en.md")
                            :default "dummy-notes-en.md")
   :tutorial/text #?(:cljs (rc/inline "tutorial-en.md")
                     :default "tutorial-en.md")
   :settings-page/edit-export-css "Editar export.css"
   :settings-page/enable-flashcards "Flashcards"
   :settings-page/export-theme "Exportar Tema"

   :discourse-title "Nosso fórum!"
   :importing "Importando"
   :asset/copy "Copiar imagem"
   :asset/delete "Excluir imagem"
   :asset/maximize "Expandir imagem"

   :asset/open-in-browser "Abrir imagem no navegador"
   :asset/show-in-folder "Mostrar imagem na pasta"
   :graph/all-graphs "Todos os grafos"
   :graph/local-graphs "Grafos locais"
   :graph/remote-graphs "Grafos remotos"
   :help/forum-community "Comunidade do fórum"
   :linked-references/filter-search "Procurar em páginas vinculadas"
   :right-side-bar/show-journals "Mostrar registros"
   :settings-page/custom-global-configuration "Configuração global personalizada"
   :settings-page/edit-global-config-edn "Editar config.edn global"
   :settings-page/sync "Sincronizar"
   :settings-page/tab-features "Recursos"
   :all-whiteboards "Todos os quadros brancos"
   :auto-heading "Título automático"
   :heading "Título {1}"
   :new-whiteboard "Novo quadro branco"
   :remove-heading "Remover título"
   :untitled "Sem título"
   :accessibility/skip-to-main-content "Ir para o conteúdo principal"
   :color/blue "Azul"
   :color/gray "Cinza"
   :color/green "Verde"
   :color/pink "Rosa"
   :color/purple "Roxo"
   :color/red "Vermelho"
   :color/yellow "Amarelo"
   :file/validate-existing-file-error "A página já existe com outro arquivo: {1}, arquivo atual: {2}. Por favor, mantenha apenas um deles e reindexe seu gráfico."
   :file-rn/all-action "Aplicar todas as ações!"
   :file-rn/apply-rename "Aplicar a operação de renomeação de arquivo"
   :file-rn/close-panel "Fechar o painel"
   :file-rn/confirm-proceed "Atualizar formato!"
   :file-rn/filename-desc-1 "Esta configuração define como uma página é armazenada em um arquivo. Logseq armazena uma página em um arquivo com o mesmo nome."
   :file-rn/filename-desc-2 "Alguns caracteres como \"/\" ou \"?\" são inválidos para um nome de arquivo."
   :file-rn/filename-desc-3 "Logseq substitui caracteres inválidos por seu URL codificado equivalente para torná-los válidos (por exemplo, \"?\" torna-se \"%3F\")."
   :file-rn/filename-desc-4 "O separador de espaço de nomes \"/\" também é substituído por \"___\" (sublinhado triplo) para consideração estética."
   :file-rn/format-deprecated "Você está usando um formato desatualizado. A atualização para o formato mais recente é altamente recomendada. Faça backup de seus dados e feche os clientes Logseq em outros dispositivos antes da operação."
   :file-rn/instruct-1 "É um processo de 2 etapas para atualizar o formato do nome do arquivo:"
   :file-rn/instruct-2 "1. Clique em "
   :file-rn/instruct-3 "2. Siga as instruções abaixo para renomear os arquivos para o novo formato:"
   :file-rn/legend "🟢 Ações opcionais de renomeação; 🟡 Ação de renomeação necessária para evitar mudança de título; 🔴 Mudança de última hora."
   :file-rn/need-action "As ações de renomeação de arquivo são sugeridas para corresponder ao novo formato. A reindexação é necessária em todos os dispositivos quando os arquivos renomeados são sincronizados."
   :file-rn/no-action "Bom trabalho! Nenhuma ação adicional necessária."
   :file-rn/optional-rename "Sugestão: "
   :file-rn/or-select-actions " ou renomear individualmente os arquivos abaixo, então "
   :file-rn/or-select-actions-2 ". Essas ações não estarão disponíveis depois que você fechar este painel."
   :file-rn/otherwise-breaking "Ou o título se tornará"
   :file-rn/re-index "A reindexação é fortemente recomendada após a renomeação dos arquivos e em outros dispositivos após a sincronização."
   :file-rn/rename "renomeie o arquivo \"{1}\" para \"{2}\""
   :file-rn/select-confirm-proceed "Dev: formato de gravação"
   :file-rn/select-format "(Opção do modo de desenvolvedor, perigoso!) Selecione o formato do nome do arquivo"
   :file-rn/suggest-rename "Ação necessária: "
   :file-rn/unreachable-title "Aviso! O nome da página se tornará {1} no formato de nome de arquivo atual, a menos que a propriedade `title::` seja definida manualmente"
   :left-side-bar/create "Criar"
   :left-side-bar/new-whiteboard "Novo quadro branco"
   :notification/clear-all "Limpar tudo"
   :on-boarding/tour-whiteboard-home "{1} Início para seus quadros brancos"
   :on-boarding/tour-whiteboard-home-description "Os quadros brancos têm sua própria seção no aplicativo, onde você pode vê-los rapidamente, criar novos ou excluí-los facilmente."
   :on-boarding/tour-whiteboard-new "{1} Criar novo quadro branco"
   :on-boarding/tour-whiteboard-new-description "Existem várias maneiras de criar um novo quadro branco. Um deles está sempre aqui no painel."
   :on-boarding/welcome-whiteboard-modal-description "Quadros brancos são uma ótima ferramenta para brainstorming e organização. Agora você pode colocar qualquer um dos seus pensamentos da base de conhecimento ou novos próximos uns dos outros em uma tela espacial para conectar, associar e entender de novas maneiras."
   :on-boarding/welcome-whiteboard-modal-skip "Pular"
   :on-boarding/welcome-whiteboard-modal-start "Iniciar quadro branco"
   :on-boarding/welcome-whiteboard-modal-title "Um novo quadro para seus pensamentos."
   :page/show-whiteboards "Mostrar quadros brancos"
   :pdf/doc-metadata "Metadados do documento"
   :pdf/hl-block-colored "Rótulo colorido para bloco de destaque"
   :plugin.install-from-file/menu-title "Instalar de plugins.edn"
   :plugin.install-from-file/notice "Os seguintes plugins substituirão seus plugins:"
   :plugin.install-from-file/success "Todos os plugins instalados"
   :plugin.install-from-file/title "Instalar plugins de plugins.edn"
   :right-side-bar/separator "Manipulador de redimensionamento da barra lateral direita"
   :right-side-bar/whiteboards "Quadros brancos"
   :search-item/block "Bloco"
   :search-item/file "Arquivo"
   :search-item/page "Página"
   :search-item/whiteboard "Quadro branco"
   :settings-page/alpha-features "Recursos em fase Alfa"
   :settings-page/beta-features "Recursos em fase Beta"
   :settings-page/clear-cache-warning "A limpeza do cache descartará os gráficos abertos. Você perderá as alterações não salvas."
   :settings-page/custom-date-format-warning "É necessário reindexar! As referências de periódicos existentes seriam quebradas!"
   :settings-page/disable-sentry-desc "A Logseq nunca coletará seu banco de dados gráfico local ou venderá seus dados."
   :settings-page/edit-setting "Editar"
   :settings-page/enable-whiteboards "Quadros brancos"
   :settings-page/filename-format "Formato dos nomes de arquivos"
   :settings-page/login-prompt "Para acessar novos recursos antes de qualquer outra pessoa, você deve ser um Patrocinador Coletivo Aberto ou Apoiador do Logseq e, portanto, fazer o login primeiro."
   :settings-page/preferred-pasting-file "Arquivo preferência para colar"
   :settings-page/tab-assets "Recursos"
   :whiteboard/link-whiteboard-or-block "Vincular quadro branco/página/bloco"

   :shortcut.category/formatting            "Formatação"
   :shortcut.category/basics                "Básico"
   :shortcut.category/navigating            "Navegação"
   :shortcut.category/block-editing         "Edição geral de blocos"
   :shortcut.category/block-command-editing "Comandos de edição de blocos"
   :shortcut.category/block-selection       "Seleção de blocos (aperte Esc para sair)"
   :shortcut.category/toggle                "Alternar"
   :shortcut.category/others                "Outros"
   :command.editor/indent                   "Aumentar avanço de parágrafo"
   :command.editor/outdent                  "Diminuir avanço de parágrafo"
   :command.editor/move-block-up            "Mover bloco para cima"
   :command.editor/move-block-down          "Mover bloco para baixo"
   :command.editor/new-block                "Criar novo bloco"
   :command.editor/new-line                 "Nova linha no bloco actual"
   :command.editor/zoom-in                  "Aproximar / Para a frente"
   :command.editor/zoom-out                 "Afastar / Para trás"
   :command.editor/follow-link              "Seguir ligação sob o cursor"
   :command.editor/open-link-in-sidebar     "Abrir ligação na barra lateral"
   :command.editor/expand-block-children    "Expandir"
   :command.editor/collapse-block-children  "Recolher"
   :command.editor/select-block-up          "Selecionar bloco acima"
   :command.editor/select-block-down        "Selecionar bloco abaixo"
   :command.editor/select-all-blocks        "Selecionar todos os blocos"
   :command.ui/toggle-help                  "Alternar ajuda"
   :command.git/commit                      "Confirmar"
   :command.go/search                       "Pesquisar no grafo"
   :command.go/search-in-page               "Pesquisar na página atual"
   :command.ui/toggle-document-mode         "Alternar modo de documento"
   :command.ui/toggle-contents              "Alternar Conteúdo na barra lateral"
   :command.ui/toggle-theme                 "Alternar entre tema claro/escuro"
   :command.ui/toggle-right-sidebar         "Alternar barra lateral"
   :command.ui/toggle-settings              "Alternar Opções"
   :command.go/journals                     "Ir para diários"
   :command.ui/toggle-wide-mode             "Alternar largura extendida"
   :command.ui/toggle-brackets              "Alternar colchetes"
   :command.search/re-index                 "Reconstruir índice de pesquisa"
   :command.editor/bold                     "Negrito"
   :command.editor/italics                  "Itálico"
   :command.editor/insert-link              "Inserir vínculo"
   :command.editor/highlight                "Realçado"
   :command.editor/undo                     "Desfazer"
   :command.editor/redo                     "Refazer"
   :command.editor/copy                     "Copiar"
   :command.editor/cut                      "Cortar"
   :command.editor/up                       "Mover cursor para cima / Selecionar para cima"
   :command.editor/down                     "Mover cursor para baixo / Selecionar para baixo"
   :command.editor/left                     "Mover cursor para a esquerda / Abrir bloco selecionado no início"
   :command.editor/right                    "Mover cursor para a direita / Abrir bloco selecionado no final"
   :command.editor/backspace                "Retroceder / Eliminar para trás"
   :command.editor/delete                   "Deletar / Eliminar para frente"
   :command.editor/cycle-todo               "Alternar estado A FAZER do elemento"
   :command.editor/clear-block              "Apagar conteúdo do bloco"
   :command.editor/kill-line-before         "Apagar linha antes do cursor"
   :command.editor/kill-line-after          "Apagar linha depois do cursor"
   :command.editor/beginning-of-block       "Mover o cursor para o início do bloco"
   :command.editor/end-of-block             "Mover o cursor para o fim do bloco"
   :command.editor/forward-word             "Mover o cursor para a próxima palavra"
   :command.editor/backward-word            "Mover o cursor para a palavra anterior"
   :command.editor/forward-kill-word        "Apagar a próxima palavra"
   :command.editor/backward-kill-word       "Apagar a palavra anterior"
   :command.editor/open-edit                "Editar bloco selecionado"
   :command.editor/delete-selection         "Eliminar blocos selecionados"
   :command.editor/toggle-open-blocks       "Alternar blocos abertos (recolher ou expandir todos)"
   :command.auto-complete/complete          "Auto-completar: Escolha o item selecionado"
   :command.auto-complete/next              "Auto-completar: Selecione o próximo item"
   :command.auto-complete/prev              "Auto-completar: Selecione o item anterior"
   :command.auto-complete/shift-complete    "Auto-completar: Abra o item selecionado na barra lateral"
   :command.cards/forgotten                 "Cartões: Esquecido"
   :command.cards/next-card                 "Cartões: Próxima carta"
   :command.cards/recall                    "Cartões: Demorar um pouco para lembrar"
   :command.cards/remembered                "Cartões: Relembrado"
   :command.cards/toggle-answers            "Cartões: mostrar/esconder as respostas/clozes"
   :command.command/toggle-favorite         "Adicionar aos/remover dos favoritos"
   :command.command-palette/toggle          "Editar atalhos"
   :command.date-picker/complete            "Escolher data: Escolha o dia selecionado"
   :command.date-picker/next-day            "Escolher data: Selecione o próximo dia"
   :command.date-picker/next-week           "Escolher data: Selecione a próxima semana"
   :command.date-picker/prev-day            "Escolher data: Selecione o dia anterior"
   :command.date-picker/prev-week           "Escolher data: Selecione a semana anterior"
   :command.editor/escape-editing           "Sair da edição"
   :command.editor/insert-youtube-timestamp "Inserir timestamp do youtube"
   :command.editor/paste-text-in-one-block-at-point "Colar texto em um bloco no ponto"
   :command.editor/replace-block-reference-at-point "Substitua a referência do bloco pelo seu conteúdo no ponto"
   :command.editor/strikethrough            "Tachar"
   :command.go/all-pages                    "Ir para todas as páginas"
   :command.go/backward                     "Voltar"
   :command.go/flashcards                   "Trocar flashcards"
   :command.go/forward                      "Avançar"
   :command.go/graph-view                   "Ir para o grafo"
   :command.go/home                         "Volar para o inicio"
   :command.go/keyboard-shortcuts           "Ir para os atalhos do teclado"
   :command.go/next-journal                 "Ir ao proximo jornal"
   :command.go/prev-journal                 "Ir ao jornal anterior"
   :command.go/tomorrow                     "Ir para amanhã"
   :command.graph/add                       "Adicionar um grafo"
   :command.graph/open                      "Selecionar grafo para abrir"
   :command.graph/remove                    "Remover um grafo"
   :command.pdf/next-page                   "Próxima página do atual pdf doc"
   :command.pdf/previous-page               "Página anterior do atual pdf doc"
   :command.sidebar/clear                   "Limpar tudo da barra lateral direita"
   :command.sidebar/open-today-page         "Abrir a página de hoje na barra lateral direita"
   :command.ui/select-theme-color           "Selecionar as cores do tema disponível"
   :command.ui/toggle-cards                 "Trocar cartões"
   :command.ui/toggle-left-sidebar          "Trocar barra lateral esquerda"
   :command.auto-complete/open-link         "Auto-completar: Abra o item selecionado no navegador"
   :command.command/run                     "Execute o comando Git"
   :command.editor/copy-current-file        "Copiar o arquivo atual"
   :command.editor/open-file-in-default-app "Abra o arquivo no aplicativo padrão"
   :command.editor/open-file-in-directory   "Abra o arquivo na pasta"
   :command.graph/save                      "Salvar grafo atual no computador"
   :command.misc/copy                       "Copiar (copiar seleção ou referência do bloco)"
   :command.ui/goto-plugins                 "Ir para o painel de plugins"
             ;;  :command.ui/open-new-window              "Abra uma nova janela"
   :command.editor/select-down              "Selecione o conteúdo abaixo"
   :command.editor/select-up                "Selecione o conteúdo acima"
   :command.editor/copy-embed               "Copiar uma incorporação do bloco, apontando para o bloco atual"
   :command.editor/copy-text                "Copiar seleção como texto"
   :command.pdf/close                       "Fechar visualização do PDF"
   :command.go/all-graphs                   "Ir à todos os grafos"
   :command.go/electron-find-in-page        "Localizar texto na página"
   :command.go/electron-jump-to-the-next    "Ir para a próxima correspondência da sua pesquisa"
   :command.go/electron-jump-to-the-previous "Voltar para a correspondência anterior da sua pesquisa"
   :command.graph/re-index                  "Reindexar o grafo atual"

   :command.editor/new-whiteboard           "Novo quadro branco"
   :command.editor/select-parent            "Selecione o bloco pai"
   :command.go/whiteboards                  "Ir para os quadros brancos"
   :command.graph/export-as-html            "Exportar páginas de gráficos públicos como html"
   :command.pdf/find                        "PDF: Pesquisar no documento PDF atual"
   :command.sidebar/close-top               "Fechar item superior na barra lateral direita"
   :command.ui/install-plugins-from-file    "Instalar plugins de plugins.edn"})
