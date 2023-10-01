
function confirmCreatingJob() {
    return confirm('Möchten Sie wirklich diesen Job wirklich online stellen?');
}

function confirmDeleteJob() {
    return confirm('Möchten sie diesen Job wirklich löschen? ' +
        '\nAlle derzeitigen Bewerber werden gelöscht und sie können diesen Job nicht wiederherstellen!');
}

function confirmClosingJob() {
    return confirm('Möchten Sie wirklich diesen Job wirklich schließen?');
}

function confirmOpenJob() {
    return confirm('Möchten Sie wirklich diesen Job wieder öffnen?' +
        '\nBenutzer werden sich wieder bewerben können.');
}

function confirmFireWorking() {
    return confirm('Sind sie sich sicher, dass sie ihren Mitarbeiter feuern wollen?' +
        '\nMACJEE trägt nicht sorge dafür, dass das Verhältnis zwischen Arbeiter und Unternehmen geregelt ist.');
}

function confirmApplication() {
    return confirm('Sind sie sich sicher, dass sie sich bei diesem Job bewerben wollen?' +
        '\nWir empfehlen vorher das Unternehmen selbstständig zu recherchieren und zusätzlich mit dem Unternehmen kontakt aufzunehmen.' +
        '\nObwohl MACJEE versucht die Integrität der Unternehmen zu prüfen, können wir "Job-fishing" leider nicht immer eindeutig identifizieren!')
}
